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


# send serial message
SERIALPORT = "/dev/ttyACM1" # port serie du microbit connecté via usb (ttyACMX pour linux, COMXX pour windows)
BAUDRATE = 115200 # baudrate par defaut
ser = serial.Serial() # recuperation de la connexion serie

ENV = "http://simulator-api.local"

#methode d'initialisation de la connexion UART
def initUART():
    ser.port=SERIALPORT
    ser.baudrate=BAUDRATE
    ser.bytesize = serial.EIGHTBITS
    ser.parity = serial.PARITY_NONE
    ser.stopbits = serial.STOPBITS_ONE
    ser.timeout = None

    ser.xonxoff = False
    ser.rtscts = False
    ser.dsrdtr = False
    print('Starting Up Serial Monitor')
    try:
            ser.open()
    except serial.SerialException:
            print("Serial {} port not available".format(SERIALPORT))
            exit()

def sendUARTMessage(msg):
    ser.write(msg.encode())

def retrieveApi(apiData):
    print('retrieveApi')
    while True:
            response = requests.get(ENV + "/api/incidents/unresolved/list")
            apiData[:] = []
            if response != "":
                    list = response.json()
                    for incident in list:
                        if incident['code_incident'] not in apiData:
                            apiData.append(incident['code_incident'])
                    time.sleep(4)

    print('END retrieveApi')


def handle_uart(apiData):
    print('handle_uart')
    while ser.isOpen() :
            for incident in apiData:
                print(incident)
                sendUARTMessage(incident)
                time.sleep(2)
                if incident in apiData:
                	apiData.remove(incident)
            time.sleep(1)
    print('END handle_uart')

# Main program logic follows:
if __name__ == '__main__':
    initUART() # initialisation uart
    print ('Press Ctrl-C to quit.')
    with Manager() as manager:
        apiData = manager.list()
        api = Process(target=retrieveApi, args= (apiData,))
        api.start()

        uart = Process(target=handle_uart, args= (apiData,))
        uart.start()

        api.join()
        uart.join()

    ser.close()
    exit()
