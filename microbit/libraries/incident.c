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
 * 
 * @return
 * A new string allocate in memory
 */
char *to_string_incident(
    incident *icd
) {
    char *str = malloc(STR_SIZE * sizeof(char));
    sprintf(str, "x:%.2f;y:%.2f;v:%.2f#", icd->latitude, icd->longitude, icd->intensity);

    return str;

} /* to_string_incident */

/* @brief
 * Create a string from an incidents object.
 * 
 * @params
 * icds : Object to transform to string : incidents *
 * 
 * @return
 * A new string allocate in memory
 */
char *to_string_incidents(
    incidents *icds
) {
    char    *str = malloc(STR_SIZE * DATA_SIZE * sizeof(char)),
            *str_temp = NULL;

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
                str_temp = to_string_incident(icd);
                strcat(str, str_temp);
                delete_string_incident(str_temp);

            }

        }

    } /* Foreach incident */

    return str;

} /* to_string_incident */

/* @brief
 * Delete a string incident.
 * 
 * @params
 * str: String to delet : char *
 */
void delete_string_incident(
    char *str
) {
    /* Desallocate memory */
    free(str);

} /* delete_string_incident */