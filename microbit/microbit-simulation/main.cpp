#include "MicroBit.h"

extern "C" {
    #include "incident.h"
};

MicroBit uBit;
MicroBitSerial serial(USBTX, USBRX);

int main()
{
    // Initialise the micro:bit runtime.
    uBit.init();
    
    serial.baud(115200);
    
    //TEst
    float   latitude = 20.2f,
            longitude = 15.5f,
            intensity = 9.9f;
    
    incident *icd = create_object_incident(longitude, latitude, intensity);
    char str[STR_SIZE];
    to_string_incident(icd, str);
    uBit.display.scroll(str);
    delete_incident(icd);
    icd = NULL;
    
    while(1){
        int c = serial.read(ASYNC);
        if ((char)c!=MICROBIT_NO_DATA){
            if ((char)c != '#') {
                ManagedString cms((char)c);
                uBit.display.scroll(cms);
            } else {
            }
            
        }
        //uBit.sleep(100);
    }
    
    release_fiber();
}

