#include <stdio.h>
#include <stdlib.h>

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

    return icds;

} /* new_incidents */

/* @brief
 * Delete an incident object.
 *
 * @params
 * icd : Object to delete.
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
 * icds : Objet to delete.
 */
void delete_incidents(
    incidents *icds
) {
    int i = 0;

    for(i; i < DATA_SIZE; i++) {
        delete_incident(icds->icd[i]);
        icds->icd[i] = NULL;
    } /* Foreach incident */

    /* Desallocate memory */
    free(icds);

} /* delete_incidents */