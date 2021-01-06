#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "incident.h"

/* @brief
 * Create an incident object with parameters.
 *
 * @params
 * longitude : Longitude of the incident : float.
 * latitude : Latitude of the incident : float.
 * intensity : Intensity of the incident : float.
 *
 * @return
 * A new incident allocate in memory.
 */
incident *create_object_incident(
    float longitude,
    float latitude,
    float intensity
) {
    /* Create the object */
    incident *icd = new_incident();

    icd->longitude = longitude;
    icd->latitude = latitude;
    icd->intensity = intensity;

    return icd;

} /* create_object_incident */

/* @brief
 * Create an incident object from a string.
 * Exemple of string : x:15.30;y:10.21;v:9.89
 * x : latitude
 * y : longitude
 * v : intensity
 * ; : delimiter between x, y and v
 * : : delimiter between the name (x, y or v) and the value
 * 
 * @params
 * str : String to convert into object : char *
 * 
 * @return
 * A new incident allocate in memory or NULL in error case.
 */
incident *create_object_incident_from_string(
    const char *str
) {
    char delim[] = ";:";
    char *token = strtok(str, delim);

    int x = 0, 
        y = 0, 
        v = 0;

    incident *icd = new_incident();
    
    while( token != NULL ) {
        if (strcmp("x", token) == 0)
            x = 1;
        else if (strcmp("y", token) == 0)
            y = 1;
        else if (strcmp("v", token) == 0)
            v = 1;
        else {
            if (x == 1) {
                float latitude = atof(token);

                icd->latitude = latitude;
                x = 0;

            } else if (y == 1) {
                float longitude = atof(token);

                icd->longitude = longitude;
                y = 0;

            } else if (v == 1) {
                float intensity = atof(token);

                icd->intensity = intensity;
                v = 0;

            } else {
                /* Delete the object */
                delete_incident(icd);
                return NULL;

            } /* Error case */

        } /* Value case */

        token = strtok(NULL, delim);
        
    } /* For each token */

    return icd;

} /* create_object_incident_from_string */

/* @brief
 * Create an incident object.
 *
 * @return
 * A new incident allocate in memory.
 */
incident *new_incident() {
    incident *icd = calloc(1, sizeof(incident));

    return icd;

} /* new_incident */

/* @brief
 * Create an incidents object.
 * By default DATA_SIZE number of incident.
 *
 * @return
 * A new incidents allocate in memory.
 */
incidents *new_incidents() {
    incidents *icds = calloc(1, sizeof(incidents));

    int i = 0;

    for(i; i < DATA_SIZE; i++) {
        icds->icd[i] = NULL;
        
    } /* Foreach incident */

    return icds;

} /* new_incidents */

/* @brief
 * Create an incident object from a string.
 * If no error, add it to the incidents object.
 * See more at the function create_object_incident_from_string.
 * 
 * @params
 * icds : Incidents object : incidents *
 * str : String to convert into object : char *
 * 
 * @return
 * 1 in case of success or 0 in error case.
 */
int add_incident_from_string(
    incidents *icds,
    const char *str
) {
    /* Check no icds */
    if (!icds)
        return 0;

    /* Create object */
    incident *icd = create_object_incident_from_string(str);

    if (icd) {
        int i = 0;
        
        for(i; i < DATA_SIZE; i++) {
            if (!icds->icd[i]) {
                icds->icd[i] = icd;
                return 1;

            } /* Add it in NULL place */

        } /* For each incident */

    } /* No error */

    return 0;

} /* add_incident_from_string */

/* @brief
 * Delete an incident object.
 *
 * @params
 * icd : Object to delete : incident *
 */
void delete_incident(
    incident *icd
) {
    icd->longitude = -1;
    icd->latitude = -1;
    icd->intensity = -1;

    /* Desallocate memory */
    free(icd);

} /* delete_incident */

/* @brief
 * Delete an incidents object
 * And delete all incident object inside.
 *
 * @params
 * icds : Objet to delete : incidents *
 */
void delete_incidents(
    incidents *icds
) {
    int i = 0;

    for(i; i < DATA_SIZE; i++) {
        if (icds->icd[i]) {
            delete_incident(icds->icd[i]);
            icds->icd[i] = NULL;
        }

    } /* Foreach incident */

    /* Desallocate memory */
    free(icds);

} /* delete_incidents */

/* @brief
 * Create a string from an incident object.
 * 
 * @params
 * icd : Object to transform to string : incident *
 * str : String which contain the result : char * of size STR_SIZE
 */
void to_string_incident(
    incident *icd,
    char *str
) {
    sprintf(str, "x:%.2f;y:%.2f;v:%.2f#", icd->latitude, icd->longitude, icd->intensity);

} /* to_string_incident */

/* @brief
 * Create a string from an incidents object.
 * 
 * @params
 * icds : Object to transform to string : incidents *
 * str : String which contain the result : char * of size STR_SIZE * DATA_SIZE
 */
void to_string_incidents(
    incidents *icds,
    char *str
) {
    int i = 0,
        first = 0;

    incident *icd = NULL;

    for(i; i < DATA_SIZE; i++) {
        icd = icds->icd[i];

        if (icd) {
            
            if (first == 0){
                /* First case */
                sprintf(str, "x:%.2f;y:%.2f;v:%.2f#", icd->latitude, icd->longitude, icd->intensity);
                first = 1;

            } else {
                /* Concate string */
                char str_temp[STR_SIZE];
                to_string_incident(icd, str_temp);
                strcat(str, str_temp);

            }

        }

    } /* Foreach incident */

} /* to_string_incident */