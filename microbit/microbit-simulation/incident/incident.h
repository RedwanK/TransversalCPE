#ifndef __INCIDENT_H__
#define __INCIDENT_H__

/* Size of an array of incidents */
#define DATA_SIZE 10 // Cant save 60 -> micro:bit memory error

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
 * Create an incident object from a string.
 * Exemple of string : x:15.30;y:10.21;v:9.89
 * x : latitude
 * y : longitude
 * v : intensity
 * ; : delimiter between x, y and v
 * : : delimiter between the name (x, y or v) and the value
 */
incident *create_object_incident_from_string(
    const char *str
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
 * Create an incident object from a string.
 * If no error, add it to the incidents object.
 * See more at the function create_object_incident_from_string.
 */
int add_incident_from_string(
    incidents *icds,
    const char *str
);

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
