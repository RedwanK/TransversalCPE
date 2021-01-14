# Crontab to synchronize simulator and emergency api

Rajouter au fichier /etc/crontab les lignes :
```
#Cron toutes les 60 secondes
* * * * * root /usr/sbin/cron.10sec > /dev/null
```

Création du répertoire qui a les scripts à exécuter toutes les 10 secondes:
```
mkdir /etc/cron.10sec
```

Création du script /usr/sbin/cron.10sec :
```
touch /usr/sbin/cron.10sec
```
puis vim (ou nano) sur /usr/sbin/cron.10sec 
```
#!/bin/bash
for COUNT in `seq 5` ; do
    run-parts --report /etc/cron.10sec
    sleep 10
done
```

Modifier les droits du script /usr/sbin/cron.10sec : 
```
chmod 755 /usr/sbin/cron.10sec
```

Créer le script à executer toutes les 10 secondes : 
```
touch /etc/cron.10sec/update-simulator
chmod 755 /etc/cron.10sec/update-simulator
```

Ensuite vim (ou nano) sur /etc/cron.10sec/update-simulator : 
```
#!/bin/bash
curl "http://simulator-api.local/api/emergency/update
```

Si le cron ne fonctionne pas tout de suite : 
```
source ~/.bashrc
```

#watch every 2 sc
```
watch -n 2 ./update-emergency.sh
```
