#ifndef __INCIDENT_H__
#define __INCIDENT_H__

/* Size of an array of incidents */
#define DATA_SIZE 60

/* Size of an array of incidents */
#define STR_SIZE 30

/* Object for create a incident */
typedef struct {
    float longitude;
    float latitude;
    float intensity;
} incident;

/* Object for create a incident */
typedef struct {
    incident *icd[DATA_SIZE];
} incidents;

/* @brief
 * Create an incident object with parameters.
 */
incident *create_object_incident(
    float longitude,
    float latitude,
    float intensity
);

/* @brief
 * Create an incident object.
 */
incident *new_incident();

/* @brief
 * Create an incidents object.
 * By default DATA_SIZE incident.
 */
incidents *new_incidents();

/* @brief
 * Delete an incident object.
 */
void delete_incident(
    incident *icd
);

/* @brief
 * Delete an incidents object
 * And delete all incident object inside.
 */
void delete_incidents(
    incidents *icds
);

/* @brief
 * Create a string from an incident object.
 */
void to_string_incident(
    incident *icd,
    char *str
);

/* @brief
 * Create a string from an incidents object.
 */
void to_string_incidents(
    incidents *icds,
    char *str
);

#endif
