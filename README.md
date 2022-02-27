# Suburbs

Simple REST API using Spring Boot and PostgresSQL.

You will need to set up a PostgresSQL table called 'suburb' for this api to connect to and work properly.

# Endpoints

POST `/suburb/save`

This endpoint will take in a `name` and `postcode` of a suburb and save it to the database.

Expected request body:

```
[
    {
        "name": "Sydney",
        "postcode": 2000
    }, 
    {
        "name": "Barangaroo",
        "postcode": 2000
    }
]
```

Returns:

```
[
    {
        "id": 1
        "name": "Sydney",
        "postcode": 2000
    }, 
    {
        "id": 2
        "name": "Barangaroo",
        "postcode": 2000
    }
]
```

POST `/suburb/retrieve`

This endpoint will take in a postcode range and return the list of suburb names belonging to the postcode range (sorted
alphabetically) and the total number characters of all the names combined.

Expected request body:

```
{
    "start": 2000,
    "end": 2001
}
```

Returns:
```
{
    "names": ["Barangaroo", "Sydney"],
    "totalNoOfCharacters": 16
}
```

# Limitations

POST `suburb/save` does not perform any checks on the data and will allow the user to save the same information multiple
time. A future area of improvement will be to perform a check to ensure that the data to be saved does not already exist
in the database. 