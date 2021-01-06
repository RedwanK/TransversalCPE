#include "MicroBit.h"

/* Include C libraries */
extern "C" {
    #include "incident.h"
};

/* Constant */
#define SN_SIZE 2
#define KEY_SIZE 8
#define NUMBER_SN 3
#define M_PROTOCOL_SIZE 7
static const char m_protocol_separator = ':';
static const char p_protocol_separator = ';';
static const char init[] = "INIT";
static const char ack[] = "ACK";
static const char stop[] = "STOP";
static const char trusted_address[NUMBER_SN][SN_SIZE] =
{ 
    "1",
    "3"
};

/* Global var */
int connected = 0;
int send_initialise = 0;
int send_acquittement = 0;
char serial_number[SN_SIZE] = "2";
char key[KEY_SIZE];
char connected_address[NUMBER_SN][SN_SIZE];
int connected_number = 0;

MicroBit uBit;

/* Initialise the serial usb port */
MicroBitSerial serial(USBTX, USBRX);

MicroBitMessageBus bus;
MicroBitDisplay display;

/* @brief
 * Read values from serial.
 * When there are values, add the values to the string
 * until there isn't a #. Then, create a incident object.
 */
void readSerial();

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

void send_incident(char *str_icd, int dest);

void generate_random_key();

void set_key(char *tmp_key);

int main()
{
    initialise();

    /* Create fiber for network and schedule it */
    create_fiber(network_protocol);

    /* Create fiber for serial and schedule it */
    /* The fiber is created when read until a value */
    readSerial();
    
    release_fiber();
    
} /* main */

/* @brief
 * Read values from serial.
 * When there are values, add the values to the string
 * until there isn't a #. Then, create a incident object.
 */
void readSerial() {
    int length;
    
    incident *icd = NULL;

    while(1){
        /* Read serial */
        ManagedString r = serial.readUntil("#");
        
        length = r.length();
        
        /* Check length */
        if (length >= STR_SIZE)
            continue;
        
        const char *str = r.toCharArray();
        
        icd = create_object_incident_from_string(str);
        
        if (icd && connected == 1) {
            char str_icd[STR_SIZE];
            to_string_incident(icd, str_icd);
            
            /* Send incidents in radio */
            send_incident(str_icd, 0);

            /* Delete incident */
            delete_incident(icd);
            
        } /* Incident not NULL */
        
    }

} /* readSerial */

void receive_protocol() {
    /* Receive packet */
    PacketBuffer pb = uBit.radio.datagram.recv();
        
    /* Check if the paquet is protocol */
    if ((char)pb[0] == '#') {
        //display.scroll(pb);
        /* Protocol case */
        if ((char)pb[1] == 'I') {
            /* INIT case */
            int i = 0,
                j = 0,
                error = 0;
                
            while(init[i] != '\0') {
                if (pb[i + 1] != init[i]) {
                    error = 1;    
                }
                i++;
                
            } /* Check no errors */
            
            if (error == 1)
                return;
                
            /* Check there is separator */
            i++;
            if (pb[i] != m_protocol_separator)
                return;
                
            /* Get address */
            i++;
            char address[SN_SIZE];
            while(pb[i] != '\0') {
                if (j >= SN_SIZE)
                    break;

                address[j] = pb[i];
                i++;
                j++;
                
            } /* Loop until end of packet */
            
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
                    error = 1;    
                }
                i++;
                
            } /* Check no errors */
            
            if (error == 1)
                return;
                
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
                            if ( j >= KEY_SIZE) {
                                error = 1;
                                break;

                            } /* Check if this is the end of packet */

                            tmp_key[j] = pb[i];
                            i++;
                            j++;
                            
                        } /* Loop until end of packet */
                        
                        tmp_key[j] = '\0';

                        if (send_acquittement == 0) {
                            if (error == 0) {
                                set_key(tmp_key);

                                /* Connected */
                                connect(address);

                                send_ack(0);

                                display.print("C");

                            } /* Set the key */

                        } else {
                            if (error == 0) {
                                /* The other micorbit already in communication so get his key */
                                set_key(tmp_key);
                                /* Connected */
                                connect(address);

                            } /* If there is a key set it */

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
            int i = 0,
                error = 0;
                
            while(stop[i] != '\0') {
                if (pb[i + 1] != stop[i]) {
                    error = 1;    
                }
                i++;
                
            } /* Check no errors */
            
            if (error == 1)
                return;

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
        
    } else {
        /* Communication case 
        if (connected == 1) {
            display.scroll(pb);
        } 
        */
        
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
    /* Initialise the heap allocator */
    //microbit_create_heap(MICROBIT_SD_GATT_TABLE_START + MICROBIT_SD_GATT_TABLE_SIZE, MICROBIT_SD_LIMIT);
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

    PacketBuffer pb(M_PROTOCOL_SIZE + SN_SIZE);
    
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
    
    uBit.radio.datagram.send(pb);

    send_initialise = 1;
    
} /* send_init */

void send_stop() {
    /* Stop the connexion */
    display.print("S");

    fiber_sleep(600);

    PacketBuffer pb(M_PROTOCOL_SIZE + SN_SIZE);
    
    pb[0] = '#';
    
    int i = 0;
    while(stop[i] != '\0') {
        pb[i + 1] = stop[i];
        i++;
        
    } /* Copy stop in the packet buffer */
    
    /* Add end line */
    i++;
    pb[i] = '\0';

    uBit.radio.datagram.send(pb);
    
} /* send_stop */

void send_ack(int cipher) {
    /* Ack the connexion */
    display.print("A");

    fiber_sleep(600);

    PacketBuffer pb(M_PROTOCOL_SIZE + SN_SIZE + KEY_SIZE);
    
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
    
    uBit.radio.datagram.send(pb);

    send_acquittement = 1;

} /* send_ack */

void send_incident(char *str_icd, int dest) {
    /* Send incident */
    PacketBuffer pb(M_PROTOCOL_SIZE + STR_SIZE);
        
    int i = 0,
        j = 0;
    while(serial_number[i] != '\0') {
        pb[i] = serial_number[i];
        i++;
        
    } /* Copy serial number in the packet buffer */
    
    /* Add separator */
    pb[i] = p_protocol_separator;
    
    i++;
    while(connected_address[dest][j] != '\0') {
        pb[i] = connected_address[dest][j];
        i++;
        j++;
        
    } /* Copy dest serial number in the packet buffer */

    /* Add separator */
    pb[i] = p_protocol_separator;
    
    i++;
    j = 0;
    while(str_icd[j] != '\0') {
        pb[i] = str_icd[j];
        i++;
        j++;
        
    } /* Copy incidents in the packet buffer */

    /* Add end line */
    pb[i] = '\0';

    uBit.radio.datagram.send(pb);
    
} /* send_incident */

void generate_random_key() {
    char c;
    int r;

    /* Initialize the random number generator */
    srand(time(NULL));

    for (int i = 0; i < KEY_SIZE; i++) {    
        /* Generate a random number */
        r = rand() % 26;
        /* Convert to a character from a-z */
        c = 'a' + r;
        
        key[i] = c;

    } /* Loop on size of key */

} /* generate_random_key */

void set_key(char *tmp_key) {

    for (int i = 0; i < KEY_SIZE; i++) {    
        key[i] = tmp_key[i];

    } /* Loop on size of key */

} /* set_key */