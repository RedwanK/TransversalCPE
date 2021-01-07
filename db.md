# SIMULATOR DB
###incident:
- id (int - PK)
- created_at (datetime)
- updated_at (datetime)
- pending (boolean)
- resolved_at (datetime)
- spot_id (integer - FK spot)
- intensity (double)

###city:
- id (integer - PK)
- name (string)
- country (string)

###spot:
- id (integer - PK)
- latitude (double)
- longitude (double)
- city_id (integer - FK city)

# REAL DB
###vehicle:
- id (integer - PK)
- category_vehicle_id (integer - FK category_vehicle)
- fuel (string)
- site_id (integer - FK site)

###site:
- id (integer - PK)
- city_id (integer - FK city)
- zipcode (integer)
- latitude (double)
- longitude (double)
- phone_number (string)

###city:
- id (integer - PK)
- name (string)
- country (string)

###agent:
- id (integer - PK)
- firstname (string)
- lastname (string)
- street (string)
- zipcode (integer)
- city_id (integer - FK city)
- job_id (integer - FK job) 
- site_id (integer - FK site)

###job:
- id (integer - PK)
- name (string)
