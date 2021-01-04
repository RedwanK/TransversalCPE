IMPORTANT : this document is written for linux users only.

In order to get more portable, we use the docker way to deal with influxdb (and with the others services we'll need).
# Docker installation
(source : https://docs.docker.com/engine/install/ubuntu/)

## Set up the repository

1. Update the apt package index and install packages to allow apt to use a repository over HTTPS:
```bash
$ sudo apt update

$ sudo apt install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common
```

2. Add Docker’s official GPG key:
```bash
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```
Verify that you now have the key with the fingerprint 9DC8 5822 9FC7 DD38 854A  E2D8 8D81 803C 0EBF CD88, by searching for the last 8 characters of the fingerprint.

```bash
$ sudo apt-key fingerprint 0EBFCD88

pub   rsa4096 2017-02-22 [SCEA]
      9DC8 5822 9FC7 DD38 854A  E2D8 8D81 803C 0EBF CD88
uid           [ unknown] Docker Release (CE deb) <docker@docker.com>
sub   rsa4096 2017-02-22 [S]
```

3. Set up the stable repository (x86_64 / amd64 only, see link above for others architectures)
```bash
$ sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
```

## Install docker engine

```bash
 $ sudo apt update
 $ sudo apt install docker-ce docker-ce-cli containerd.io
```

# InfluxDB installation
Once we have docker correctly installed, we can install influxdb easily.

1. Pull the repository from docker
```bash
 $ sudo docker pull influxdb
```

2. Run the container as a daemon
```bash
 $ docker run --name=influxdb -d -p 8086:8086 influxdb
```

3. Enter the container
```bash
 $ docker exec -it influxdb influx
```
Now you have a beautiful influxdb shell working on your machine.

# InfluxDB configuration

Once we have influxdb up and running we just need to set up our users and databases.

Run the following steps into the influxdb shell.
1. Create the database 
```sql
CREATE DATABASE iot
```

2. Create the user you'll use
```sql
CREATE USER iot_user WITH PASSWORD 'iot'
```

3. Grant all privileges on your database to your user
```sql
GRANT ALL ON iot TO iot_user
```

And that's it, you have a fully operationnal influxdb server with a user created and a database ready to use.

To check the connection to your influxb server you can do the following :
```bash
 $ curl -G http://localhost:8086/query --data-urlencode "q=SHOW DATABASES"

{"results":[{"statement_id":0,"series":[{"name":"databases","columns":["name"],"values":[["_internal"],["iot"]]}]}]}
```

# Mosquitto installation

1. Pull the latest eclipse-mosquitto image
```bash
 $ docker pull eclipse-mosquitto
```

2. Run the mosquitto-server
```bash
 $ docker run -it --name mosquitto -p 1883:1883 eclipse-mosquitto
```

# Tests procedure

In order to test the infrastructures we've just set up, you have to run the 'MQTTBridgeInfluxDB.py' python script.

```bash
$ python3 MQTTBridgeInfluxDB.py

MQTT to InfluxDB bridge
Connected with result code 0
```
It automatically connect to the MQTT broker we launched with docker (on port 1883 by default) and wait for messages.

To send a message and see if everything works you must run 'tests/publishMQTT.py'

```bash
$ python3 publishMQTT.py 
data published 
```

# Grafana installation

1. Pull the container

```bash
 $ sudo docker pull grafana/grafana
```

2. Run the grafana container

```bash
 $ docker run -d -p 3000:3000 grafana/grafana
```

3. Open your browser and go to `127.0.0.1:3000`

4. Finally configure grafana with the GUI