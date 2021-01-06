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

    /**** Test incident from string ****/
    printf("******** Test incident from string ********\n");

    /* Create object */
    char str_incident[STR_SIZE] = "x:10.2;y:15.5;v:9.9";
    incident *icd = create_object_incident_from_string((const char *)str_incident);

    printf("Longitude : %.2f\n", icd->longitude);
    printf("Latitude : %.2f\n", icd->latitude);
    printf("Intensity : %.2f\n", icd->intensity);

    /* Test values */
    assert(icd->longitude == longitude);
    assert(icd->latitude == latitude);
    assert(icd->intensity == intensity);

    /* Delete the object */
    printf("Delete the object\n");
    delete_incident(icd);
    icd = NULL;

    /**** Test incident from string error ****/
    printf("******** Test incident from string error ********\n");

    /* Create object */
    strcpy(str_incident, ":10.2;y:15.5;v:9.9");
    icd = create_object_incident_from_string((const char *)str_incident);

    /* Test object */
    assert(icd == NULL);

    /**** Test incident ****/
    printf("******** Test incident ********\n");

    /* Create object */
    icd = new_incident();
    /* Set the values of the incident */
    icd->longitude = longitude;
    icd->latitude = latitude;
    icd->intensity = intensity;

    printf("Longitude : %.2f\n", icd->longitude);
    printf("Latitude : %.2f\n", icd->latitude);
    printf("Intensity : %.2f\n", icd->intensity);

    char str[STR_SIZE];
    to_string_incident(icd, str);
    printf("%s\n", str);
    
    /* Test values */
    assert(icd->longitude == longitude);
    assert(icd->latitude == latitude);
    assert(icd->intensity == intensity);
    /* Test to string */
    assert(strcmp("x:10.20;y:15.50;v:9.90#", str) == 0);
    
    /* Delete the object */
    printf("Delete the object\n");
    delete_incident(icd);
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
    char str_60[STR_SIZE * DATA_SIZE];
    to_string_incidents(icds, str_60);
    printf("%s\n", str_60);

    /* Test to add from a string */
    icd = icds->icd[DATA_SIZE - 5];
    delete_incident(icd);
    icd = NULL;

    icds->icd[DATA_SIZE - 5] = NULL;

    strcpy(str_incident, "x:10.2;y:15.5;v:9.9");

    int result = add_incident_from_string(icds, (const char *)str_incident);

    assert(result == 1);
    assert(icds->icd[DATA_SIZE - 5] != NULL);
    assert(icds->icd[DATA_SIZE - 5]->longitude == 15.5f);
    
    /* Delete the object */
    printf("Delete the object\n");
    delete_incidents(icds);
    icds = NULL;

    printf("******** Test passed ********\n");
    
    return 0;

} /* main */