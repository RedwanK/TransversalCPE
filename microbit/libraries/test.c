#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <assert.h>
#include <string.h>
#include <sys/types.h>

#include "test.h"

/* @brief
 * Main fonction.
 */
int main(
    int   argc,
    char  **argv
) {
    float   latitude = 10.2f,
            longitude = 15.5f,
            intensity = 9.9f;
    /**** Test incident ****/
    printf("******** Test incident ********\n");

    /* Create object */
    incident *icd = new_incident();
    /* Set the values of the incident */
    icd->longitude = longitude;
    icd->latitude = latitude;
    icd->intensity = intensity;

    printf("Longitude : %.2f\n", icd->longitude);
    printf("Latitude : %.2f\n", icd->latitude);
    printf("Intensity : %.2f\n", icd->intensity);

    char *str = to_string_incident(icd);
    printf("%s\n", str);
    
    /* Test values */
    assert(icd->longitude == longitude);
    assert(icd->latitude == latitude);
    assert(icd->intensity == intensity);
    /* Test to string */
    assert(strcmp("x:10.20;y:15.50;v:9.90#", str) == 0);
    
    /* Delete the object */
    printf("Delete the object\n");
    delete_string_incident(str);
    delete_incident(icd);
    str = NULL;
    icd = NULL;

    /**** Test incident ****/
    printf("******** Test incident ********\n");

    /* Create object */
    icd = create_object_incident(10.3f, 15.4f, 9.8f);

    printf("Longitude : %.2f\n", icd->longitude);
    printf("Latitude : %.2f\n", icd->latitude);
    printf("Intensity : %.2f\n", icd->intensity);
    
    /* Test values */
    assert(icd->longitude == 10.3f);
    assert(icd->latitude == 15.4f);
    assert(icd->intensity == 9.8f);
    
    /* Delete the object */
    printf("Delete the object\n");
    delete_incident(icd);
    icd = NULL;

    /**** Test incidents ****/
    printf("******** Test incidents ********\n");

    /* Create object */
    incidents *icds = new_incidents();
    int i = 0;
    for(i; i < DATA_SIZE; i++) {
        /* Create object */
        icd = create_object_incident(longitude, latitude, intensity);

        icds->icd[i] = icd;
        /* Test values */
        assert(icds->icd[i]->longitude == longitude);
        assert(icds->icd[i]->latitude == latitude);
        assert(icds->icd[i]->intensity == intensity);
    }

    /* Create string */
    str = to_string_incidents(icds);
    printf("%s\n", str);
    
    /* Delete the object */
    printf("Delete the object\n");
    delete_string_incident(str);
    delete_incidents(icds);
    str = NULL;
    icds = NULL;

    printf("******** Test passed ********\n");
    
    return 0;

} /* main */