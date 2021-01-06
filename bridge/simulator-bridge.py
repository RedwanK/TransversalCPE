import time
import argparse
import signal
import sys
import socket
import socketserver
import serial
from threading import Thread, Event
import time
import json
from multiprocessing import Process, Value, Lock, Array, Manager
import requests

LAST_VALUE      = b"TL" #derniere valeur remontée par le microbit (initialisée a TL arbitrairement) elle prend des valeurs json type : {t: 28, l: 0, o: "TL"}
LAST_CLIENT_ADDRESS = "" #adresse du dernier client android avec qui le serveur a communiqué
LAST_VALUE_RECEIVED = "" # dernière valeur recue


# send serial message
SERIALPORT = "/dev/tty" # port serie du microbit connecté via usb (ttyACMX pour linux, COMXX pour windows)
BAUDRATE = 115200 # baudrate par defaut
ser = serial.Serial() # recuperation de la connexion serie

#methode d'initialisation de la connexion UART
def initUART():
        # ser = serial.Serial(SERIALPORT, BAUDRATE)
        ser.port=SERIALPORT
        ser.baudrate=BAUDRATE
        ser.bytesize = serial.EIGHTBITS #number of bits per bytes
        ser.parity = serial.PARITY_NONE #set parity check: no parity
        ser.stopbits = serial.STOPBITS_ONE #number of stop bits
        ser.timeout = None          #block read

        # ser.timeout = 0             #non-block read
        # ser.timeout = 2              #timeout block read
        ser.xonxoff = False     #disable software flow control
        ser.rtscts = False     #disable hardware (RTS/CTS) flow control
        ser.dsrdtr = False       #disable hardware (DSR/DTR) flow control
        #ser.writeTimeout = 0     #timeout for write
        print('Starting Up Serial Monitor')
        try:
                ser.open()
        except serial.SerialException:
                print("Serial {} port not available".format(SERIALPORT))
                exit()

def on_publish(client,userdata,result):
    print("data published \n")
    pass

# methode d'envoi d'un message par UART (a destination du microbit)
def sendUARTMessage(msg):
    ser.write(msg) # envoi du message
    display = str(msg, 'UTF-8')
    print("Message <" + display + "> sent to micro-controller." ) #retour visuel dans la console du serveur

def retrieveApi(apiData):
        print('retrieveApi')
        while True:
                response = requests.get("http://simulator-api.local/api/incidents/list")
                if response != "":
                        print(response.json())
                        #apiData.append(response.json())
                if exit_event.wait(timeout=5):
                        break

        print('END retrieveApi')
        
def signal_handler(signum, frame):
        print('signal_handler')
        exit_event.set()

def handle_uart(apiData):
        print('handle_uart')
        while ser.isOpen() :
                print('one more round')
                if exit_event.wait(timeout=3):
                        print('exit handle_uart')
                        break
                if (ser.inWaiting() > 0): # if incoming bytes are waiting
                        data_b = ser.readline() # recuperation des données en provenance de l'UART (microbit)
                        data_str = str(data_b, 'UTF-8') #conversion des bits en string
                        if data_str == "retry\n": #si une erreur est survenue = le microbit renvoie "retry\n"
                                #sendUARTMessage(str.encode(LAST_VALUE_RECEIVED)) # alors on renvoie le dernier message envoyé pour relancer l'operation
                                print("retry <" + LAST_VALUE_RECEIVED + ">") # retour visuel
                        else:
                                LAST_VALUE = data_b # sinon, tout s'est bien passé et on a reçu des données depuis le microbit
                                print(data_str)
        print('END handle_uart')
        
# Main program logic follows:
if __name__ == '__main__':
        initUART() # initialisation uart
        print ('Press Ctrl-C to quit.')

        exit_event = Event()
        signal.signal(signal.SIGINT, signal_handler)
        

        with Manager() as manager:
                apiData = manager.list()
                apiThread = Thread(target=retrieveApi, args=(apiData,))  # Passing the list
                apiThread.daemon = False
                uartThread = Thread(target=handle_uart, args=(apiData,))
                uartThread.daemon = False
                apiThread.start()
                uartThread.start()
                apiThread.join()
                uartThread.join()
        
        ser.close()
        exit()