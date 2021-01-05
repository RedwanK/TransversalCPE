# Program to control passerelle between Android application
# and micro-controller through USB tty
import time
import argparse
import signal
import sys
import socket
import socketserver
import serial
import threading
import time
import json
import paho.mqtt.client as paho

LAST_VALUE      = b"TL" #derniere valeur remontée par le microbit (initialisée a TL arbitrairement) elle prend des valeurs json type : {t: 28, l: 0, o: "TL"}
LAST_CLIENT_ADDRESS = "" #adresse du dernier client android avec qui le serveur a communiqué
LAST_VALUE_RECEIVED = "" # dernière valeur recue
broker="127.0.0.1"
port=1883

# send serial message
SERIALPORT = "/dev/ttyACM0" # port serie du microbit connecté via usb (ttyACMX pour linux, COMXX pour windows)
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


# Main program logic follows:
if __name__ == '__main__':
        initUART() # initialisation uart
        print ('Press Ctrl-C to quit.')

        try:
                while ser.isOpen() :
                        if (ser.inWaiting() > 0): # if incoming bytes are waiting
                                data_b = ser.readline() # recuperation des données en provenance de l'UART (microbit)
                                data_str = str(data_b, 'UTF-8') #conversion des bits en string
                                if data_str == "retry\n": #si une erreur est survenue = le microbit renvoie "retry\n"
                                        sendUARTMessage(str.encode(LAST_VALUE_RECEIVED)) # alors on renvoie le dernier message envoyé pour relancer l'operation
                                        print("retry <" + LAST_VALUE_RECEIVED + ">") # retour visuel
                                else:
                                        LAST_VALUE = data_b # sinon, tout s'est bien passé et on a reçu des données depuis le microbit
                                        client1= paho.Client("microbitEmergency")
                                        client1.on_publish = on_publish
                                        client1.connect(broker,port)
                                        ret= client1.publish("iot/incident/fire", data_str)        
                                        #collection.insert_one(json.loads(data_str)) # on les sauvegarde en bdd
                                        print(data_str)
        except (KeyboardInterrupt, SystemExit): # en cas de ctrl+c on close tout et on quitte
                ser.close()
                exit()