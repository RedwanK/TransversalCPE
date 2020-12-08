incident:
- id
- coordx
- coordy
- ville
- intensité

véhicule:
- id
- type
- carburant
- caserne_id

établissement:
- id
- ville
- code_postal
(- coordx)
(- coordy)
- rue
- n°tel

agent:
- id
- nom
- prenom
- adresse
- fonction
- établissement_id

fonction:
- id
- nom
(- hiérarchie)
- agent_id
