import paho.mqtt.client as paho
broker="127.0.0.1"
port=1883
def on_publish(client,userdata,result):
    print("data published \n")
    pass
client1= paho.Client("control1")
client1.on_publish = on_publish
client1.connect(broker,port)
ret= client1.publish("iot/incident/fire","2.89")         