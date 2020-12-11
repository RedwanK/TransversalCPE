#include "MicroBit.h"

/* Include C libraries */
extern "C" {
    #include "incident.h"
};

MicroBit uBit;

/* Initialise the serial usb port */
MicroBitSerial serial(USBTX, USBRX);

/* @brief
 * Read values from serial.
 * When there are values, add the values to the string
 * until there isn't a #. Then, create a incident object and add 
 * it to the incidents object.
 */
void readSerial();

int main()
{
    /* Initialise the micro:bit runtime */
    uBit.init();
    /* Initialise the serial bauderate */
    serial.baud(115200);
    /* Initialise the heap allocator */
    microbit_create_heap(MICROBIT_SD_GATT_TABLE_START + MICROBIT_SD_GATT_TABLE_SIZE, MICROBIT_SD_LIMIT);

    /* Create fiber for serial and schedule it */
    create_fiber(readSerial);
    
    release_fiber();
    
} /* main */

/* @brief
 * Read values from serial.
 * When there are values, add the values to the string
 * until there isn't a #. Then, create a incident object and add 
 * it to the incidents object.
 *
 * first : Indicate if we are in the first case, 0 yes, 1 no
 * i : Indicate where we are in the string
 * str : String who contain the values from serial for one incident
 * icds : Incidents where we put the new incident
 */
void readSerial() {
    int first = 0,
        i = 0;
    
    char str[STR_SIZE];

    incidents *icds = new_incidents();

    while(1){
        /* Read serial */
        int v = serial.read(ASYNC);
        if (v != MICROBIT_NO_DATA){
            char c = (char)v;
            
            if (first == 0) {
                if (c != 'x')
                    continue;
                
                first = 1;    
            }
            if (c != '#') {
                if (i < STR_SIZE) {
                    /* Check we dont exceed the string */
                    str[i] = c;
                    
                } else {
                    str[STR_SIZE - 1] = '\0';
                }

                i++;

            } else {
                if (i < STR_SIZE) {
                    /* Check we dont exceed the string */
                    str[i] = '\0';

                } else {
                    str[STR_SIZE - 1] = '\0';

                }

                first = 0;
                i = 0;
                /* Create incident object from string */
                add_incident_from_string(icds, str);

                /* Empty str */
                str[0] = '\0';

            }
            
        } else {
            if (icds->icd[0]) {
                /* Create string */
                char str_60[STR_SIZE * DATA_SIZE];
                to_string_incidents(icds, str_60);
                uBit.display.scroll(str_60);

                /* Send incidents in radio */

                /* Delete incidents */
                delete_incidents(icds);
                /* Create a new one */
                icds = new_incidents();

            } /* If at least one incident is not null */

        }
    }

} /* readSerial */
