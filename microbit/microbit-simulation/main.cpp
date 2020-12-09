#include "MicroBit.h"

/* Include C libraries */
extern "C" {
    #include "incident.h"
};

MicroBit uBit;

/* Initialise the serial usb port */
MicroBitSerial serial(USBTX, USBRX);

int main()
{
    /* Initialise the micro:bit runtime */
    uBit.init();
    /* Initialise the serial bauderate */
    serial.baud(115200);
    
    /* Test struct */
    float   latitude = 20.2f,
            longitude = 15.5f,
            intensity = 9.9f;
    
    incident *icd = create_object_incident(longitude, latitude, intensity);
    char str[STR_SIZE];
    to_string_incident(icd, str);
    uBit.display.scroll(str);
    delete_incident(icd);
    icd = NULL;
    
    int first = 0,
        i = 0;
    char str[STR_SIZE];
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
                str[i] = c;
                i++;
            } else {
                first = 0;
                i = 0;
                /* Create incident object from string */

            }
            
        } else {
            //send data if exist  
           // ManagedString cms((c);
             //       uBit.display.scroll(cms);  
        }
        //uBit.sleep(100);
    }
    
    release_fiber();
}

