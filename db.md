incident:
- id
- latitude
- longitude
- city_id
- intensity
- updated_at

vehicle:
- id
- type
- fuel
- site_id

site:
- id
- city_id
- postal_code
- latitude
- longitude
- street
- phone_number

agent:
- id
- firstname
- lastname
- address
- job
- site_id

job:
- id
- name
- hierarchy
- agent_id

audit:
- id
[...incident data...]
    - updated_at
    - city_name
    - lat
    - long
    - intensity