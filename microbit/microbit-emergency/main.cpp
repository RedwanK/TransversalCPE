#include "MicroBit.h"

/* Include C libraries */
extern "C" {
    #include "incident.h"
    #include "cipher.h"
};

/* Constant */
#define SN_SIZE 2
#define KEY_SIZE 8
#define NUMBER_SN 3
#define M_PROTOCOL_SIZE 7
const char m_protocol_separator = ':';
const char p_protocol_separator = ';';
const char init[] = "INIT";
const char ack[] = "ACK";
const char stop[] = "STOP";
const char trusted_address[NUMBER_SN][SN_SIZE] =
{ 
    "2",
    "3"
};
/* Default key */
const char default_key[] = "J'chlibs";

/* Global var */
int connected = 0;
int send_initialise = 0;
int send_acquittement = 0;
char serial_number[SN_SIZE] = "1";
char key[KEY_SIZE];
char connected_address[NUMBER_SN][SN_SIZE];
int connected_number = 0;

MicroBit uBit;

/* Initialise the serial usb port */
MicroBitSerial serial(USBTX, USBRX);

MicroBitMessageBus bus;
MicroBitDisplay display;

void receive_protocol();

int check_address(char *address);

int check_connected(char *address);

void connect(char *address);

void send_protocol();

void network_protocol();

void initialise();

void send_init();

void send_stop();

void send_ack(int cipher);

void generate_random_key();

void set_key(char *tmp_key);

int main()
{
    initialise();

    /* Create fiber for network and schedule it */
    create_fiber(network_protocol);
    
    release_fiber();
    
} /* main */

void receive_protocol() {
    /* Receive packet */
    PacketBuffer r = uBit.radio.datagram.recv();

    char d[1], e[1];
    e[0] = (char)r[0];
    decrypt(default_key, e, d);
    
    /* Check if the paquet is protocol */
    if (d[0] == '#') {
        //display.scroll(pb);

        /* Decrypt message */
        char pb_e[MICROBIT_RADIO_MAX_PACKET_SIZE],
            pb[MICROBIT_RADIO_MAX_PACKET_SIZE];
        int x = 0;
        while(r[x] != '\0' && x < MICROBIT_RADIO_MAX_PACKET_SIZE) {
            pb_e[x] = (char)r[x];
            x++;

        } /* Get all message */

        if (x >= MICROBIT_RADIO_MAX_PACKET_SIZE)
            pb_e[MICROBIT_RADIO_MAX_PACKET_SIZE - 1] = '\0';
        else
            pb_e[x] = '\0';
        
        decrypt(default_key, pb_e, pb);

        /* Protocol case */
        if (pb[1] == 'I') {
            /* INIT case */
            int i = 0,
                j = 0;
                
            while(init[i] != '\0') {
                if (pb[i + 1] != init[i]) {
                    return;
                }
                i++;
                
            } /* Check no errors */
                
            /* Check there is separator */
            i++;
            if (pb[i] != m_protocol_separator)
                return;
                
            /* Get address */
            i++;
            char address[SN_SIZE];
            while(pb[i] != '\0') {
                if (j >= SN_SIZE)
                    return;

                address[j] = pb[i];
                i++;
                j++;
                
            } /* Loop until end of packet */

            if (j >= SN_SIZE)
                return;
            
            address[j] = '\0';
            
            /* Check the address is trusted */
            int found = check_address(address);

            /* Check the address is not connected */
            int found_c = check_connected(address);
            
            if (found == 1 && found_c == 0) {
                if (send_initialise == 0) {
                    /* Send init */
                    send_init();

                } else {
                    /* Send ack */
                    send_ack(1);

                }
                
            } /* Address is trusted */
        } else if (pb[1] == 'A') {
            /* ACK case */
            int i = 0,
                j = 0,
                error = 0;
                
            while(ack[i] != '\0') {
                if (pb[i + 1] != ack[i]) {
                    return;
                }
                i++;
                
            } /* Check no errors */
                
            /* Check there is separator */
            i++;
            if (pb[i] != m_protocol_separator)
                return;
                
            /* Get address */
            i++;
            char address[SN_SIZE];
            while(pb[i] != ':') {
                if (pb[i] == '\0' || j >= SN_SIZE) {
                    error = 1;
                    break;

                } /* Check if this is the end of packet */

                address[j] = pb[i];
                i++;
                j++;
                
            } /* Loop until end of packet or before separator */

            if (j >= SN_SIZE)
                return;
            
            address[j] = '\0';
            
            /* Check the address is trusted */
            int found = check_address(address);

            /* Check the address is not connected */
            int found_c = check_connected(address);
            
            if (found == 1 && found_c == 0) {
                if (connected == 0) {
                    /* Not connected */
                    
                    if (error == 0) {
                        /* No error */
                        /* Get the key */
                        i++;
                        j = 0;
                        char tmp_key[KEY_SIZE];
                        while(pb[i] != '\0') {
                            if (j >= KEY_SIZE) {
                                return;

                            } /* Check if this is the end of packet */

                            tmp_key[j] = pb[i];
                            i++;
                            j++;
                            
                        } /* Loop until end of packet */

                        if (j == KEY_SIZE)
                            return;
                        
                        tmp_key[j] = '\0';

                        if (send_acquittement == 0) {
                            /* Set the key */
                            set_key(tmp_key);

                            /* Connected */
                            connect(address);

                            send_ack(0);

                            display.print("C");

                        } else {
                            /* The other micorbit already in communication so get his key */
                            set_key(tmp_key);
                            /* Connected */
                            connect(address);

                        }

                    } else {
                        if (send_acquittement == 1) {
                            /* Normal there is no key */
                            /* Connected */
                            connect(address);
                        }
                    }

                } else {
                    /* Already connected so send the current key */

                    /* Connected */
                    connect(address);

                    /* Send ack */
                    send_ack(1);

                    display.print("C");

                }
                
            } /* Address is trusted */
        } else if (pb[1] == 'S') {
            /* STOP case */
            int i = 0;
                
            while(stop[i] != '\0') {
                if (pb[i + 1] != stop[i]) {
                    return;
                }
                i++;
                
            } /* Check no errors */

            if (connected == 1) {
                send_initialise = 0;
                send_acquittement = 0;
                connected = 0;
                connected_number = 0;
                for (int x = 0; x < NUMBER_SN; x++) {
                    connected_address[x][0] = '\0';
                    
                } /* For each address */
                send_stop();
                display.print("D");

            } /* Stop the connection */

        }
        
    } else if (connected == 1) {
        /* Decrypt message */
        char pb_e[MICROBIT_RADIO_MAX_PACKET_SIZE],
            pb[MICROBIT_RADIO_MAX_PACKET_SIZE];
        int x = 0;
        while(r[x] != '\0' && x < MICROBIT_RADIO_MAX_PACKET_SIZE) {
            pb_e[x] = (char)r[x];
            x++;

        } /* Get all message */

        if (x >= MICROBIT_RADIO_MAX_PACKET_SIZE)
            pb_e[MICROBIT_RADIO_MAX_PACKET_SIZE - 1] = '\0';
        else
            pb_e[x] = '\0';
        
        decrypt(key, pb_e, pb);

        /* Communication case */
        int i = 0,
            j = 0;
            
        /* Get source address */
        char address[SN_SIZE];
        while(pb[i] != ';') {
            if (pb[i] == '\0' || i >= SN_SIZE) {
                return;

            } /* Check if this is the end of packet */

            address[i] = pb[i];
            i++;
            
        } /* Loop until separator */
        
        if (i == SN_SIZE)
            return;

        address[i] = '\0';
        
        /* Check the address is connected */
        int found = check_connected(address);

        if (found == 1) {
            /* Check there is separator */
            if (pb[i] != p_protocol_separator)
                return;
            
            /* Get dest address */
            i++;
            while(pb[i] != ';') {
                if (pb[i] == '\0' || j >= SN_SIZE) {
                    return;

                } /* Check if this is the end of packet */

                address[j] = pb[i];
                i++;
                j++;
                
            } /* Loop until separator */
            
            if (j == SN_SIZE)
                return;

            address[j] = '\0';

            /* Check this is our address */
            for (j = 0; j < SN_SIZE; j++) {
                if (address[j] != serial_number[j])
                    return;
                    
            } /* Loop on address */

            /* Check there is separator */
            if (pb[i] != p_protocol_separator)
                return;
            
            /* Get incident */
            i++;
            j = 0;
            char str_icd[STR_SIZE];
            while(pb[i] != '\0') {
                if (j >= STR_SIZE) {
                    return;

                } /* Check if this is the end of packet */

                str_icd[j] = pb[i];
                i++;
                j++;
                
            } /* Loop until end of packet */

            if (j == KEY_SIZE)
                return;
            
            str_icd[j] = '\0';

            incident *icd = create_object_incident_from_string((const char *)str_icd);

            if (icd) {
                char str[STR_SIZE];
                to_string_incident(icd, str, 1);

                /* Send to serial */
                serial.send(str);
                //display.scroll(str);

            } /* Incident not NULL */
        }
        
    }

    fiber_sleep(50);
    
} /* receive_protocol */

int check_address(char *address) {
    for (int i = 0; i < NUMBER_SN; i++) {
        int error = 0;

        for (int j = 0; j < SN_SIZE; j++) {
            if (trusted_address[i][j] != address[j])
                error = 1;
                
        } /* Loop on address */
        
        /* Check no error */
        if (error == 0)
            return 1;
        
    } /* For each trusted address */
    
    return 0;
    
} /* check_address */

int check_connected(char *address) {
    if (connected_number == 0)
        return 0;
    
    for (int i = 0; i < connected_number; i++) {
        int error = 0;

        for (int j = 0; j < SN_SIZE; j++) {
            if (connected_address[i][j] != address[j])
                error = 1;
                
        } /* Loop on address */
        
        /* Check no error */
        if (error == 0)
            return 1;
        
    } /* For each connected address */
    
    return 0;
    
} /* check_connected */

void connect(char *address) {    
    if (connected_number < NUMBER_SN) {
        display.print("C");
        connected = 1;
        send_initialise = 0;
        send_acquittement = 0;

        for (int j = 0; j < SN_SIZE; j++) {
            connected_address[connected_number][j] = address[j];
                
        } /* Loop on address */

        connected_number++;

    } /* Check not too many address */

} /* connect */

void send_protocol() {
    if (uBit.buttonA.isPressed() && connected == 0) {
        /* INIT case */
        send_init();
        
    } else if (uBit.buttonB.isPressed() && connected == 1) {
        /* Stop the connexion */
        send_stop();
        
    }

    fiber_sleep(50);

} /* send_protocol */

void network_protocol() {
    while(1)
    {
        send_protocol();
        receive_protocol();
    }

} /* network_protocol */

void initialise() {
    /* Initialise the micro:bit runtime */
    uBit.init();
    /* Initialise the serial bauderate */
    serial.baud(115200);
    /* Initialise the radio */
    scheduler_init(bus);
    //bus.listen(MICROBIT_ID_RADIO, MICROBIT_RADIO_EVT_DATAGRAM, receive_protocol);
    uBit.radio.enable();
    
    /* Default disconnected */
    connected = 0;
    send_initialise = 0;
    send_acquittement = 0;
    display.print("D");
    
} /* initialise */

void send_init() {
    /* Initialise the connexion */
    display.print("I");

    fiber_sleep(600);

    char pb[M_PROTOCOL_SIZE + SN_SIZE];
    
    pb[0] = '#';
    
    int i = 0,
        j = 0;
    while(init[i] != '\0') {
        pb[i + 1] = init[i];
        i++;
        
    } /* Copy init in the packet buffer */
    
    /* Add separator */
    i++;
    pb[i] = m_protocol_separator;
    
    i++;
    while(serial_number[j] != '\0') {
        pb[i] = serial_number[j];
        i++;
        j++;
        
    } /* Copy serial number in the packet buffer */
    
    /* Add end line */
    pb[i] = '\0';
    
    /* Encryption */
    char encryptedMsg[M_PROTOCOL_SIZE + SN_SIZE];
    encrypt(default_key, pb, encryptedMsg);
    
    PacketBuffer s(M_PROTOCOL_SIZE + SN_SIZE);
    
    for (i = 0; i < M_PROTOCOL_SIZE + SN_SIZE; i++) {
        s[i] = encryptedMsg[i];
            
    } /* Copy in buffer */
    
    uBit.radio.datagram.send(s);

    send_initialise = 1;
    
} /* send_init */

void send_stop() {
    /* Stop the connexion */
    display.print("S");

    fiber_sleep(600);

    char pb[M_PROTOCOL_SIZE + SN_SIZE];
    
    pb[0] = '#';
    
    int i = 0;
    while(stop[i] != '\0') {
        pb[i + 1] = stop[i];
        i++;
        
    } /* Copy stop in the packet buffer */
    
    /* Add end line */
    i++;
    pb[i] = '\0';
    
    /* Encryption */
    char encryptedMsg[M_PROTOCOL_SIZE + SN_SIZE];
    encrypt(default_key, pb, encryptedMsg);

    PacketBuffer s(M_PROTOCOL_SIZE + SN_SIZE);
    
    for (i = 0; i < M_PROTOCOL_SIZE + SN_SIZE; i++) {
        s[i] = encryptedMsg[i];
            
    } /* Copy in buffer */
    
    uBit.radio.datagram.send(s);
    
} /* send_stop */

void send_ack(int cipher) {
    /* Ack the connexion */
    display.print("A");

    fiber_sleep(600);

    char pb[M_PROTOCOL_SIZE + SN_SIZE + KEY_SIZE];
    
    pb[0] = '#';
    
    int i = 0,
        j = 0;
    while(ack[i] != '\0') {
        pb[i + 1] = ack[i];
        i++;
        
    } /* Copy ack in the packet buffer */
    
    /* Add separator */
    i++;
    pb[i] = m_protocol_separator;
    
    i++;
    while(serial_number[j] != '\0') {
        pb[i] = serial_number[j];
        i++;
        j++;
        
    } /* Copy serial number in the packet buffer */

    if (cipher == 1) {
        /* Add separator */
        pb[i] = m_protocol_separator;

        /* Generate random key */
        if (connected == 0) {
            generate_random_key();

        } /* If not connected generate a key */

        i++;
        j = 0;
        while(key[j] != '\0') {
            pb[i] = key[j];
            i++;
            j++;
            
        } /* Copy key in the packet buffer */
    }
    
    /* Add end line */
    pb[i] = '\0';
    
    /* Encryption */
    char encryptedMsg[M_PROTOCOL_SIZE + SN_SIZE + KEY_SIZE];
    encrypt(default_key, pb, encryptedMsg);
    
    PacketBuffer s(M_PROTOCOL_SIZE + SN_SIZE + KEY_SIZE);
    
    for (i = 0; i < M_PROTOCOL_SIZE + SN_SIZE + KEY_SIZE; i++) {
        s[i] = encryptedMsg[i];
            
    } /* Copy in buffer */
    
    uBit.radio.datagram.send(s);

    send_acquittement = 1;

} /* send_ack */

void generate_random_key() {
    char c;
    int r;

    for (int i = 0; i < KEY_SIZE - 1; i++) {    
        /* Generate a random number */
        r = uBit.random(26);
        /* Convert to a character from a-z */
        c = 'a' + r;
        
        key[i] = c;

    } /* Loop on size of key */

    /* Add end line */
    key[KEY_SIZE - 1] = '\0';

} /* generate_random_key */

void set_key(char *tmp_key) {

    for (int i = 0; i < KEY_SIZE; i++) {    
        key[i] = tmp_key[i];

    } /* Loop on size of key */

} /* set_key */