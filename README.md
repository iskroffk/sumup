# SumUp coding challenge
HTTP job processing service that returns tasks contained in a job in the right order, suitable for execution in the command line.
## Documentation

### Server Architecture
* ```Service``` - used for executing business logic
* ```Controller``` - used for handling http request
---

#### Service
One service has been implemented for the aim of this project:
* ```JobProcessingService``` - The service is used for creating the graph structures from the JSON passed from the controller and iterating over them.
---
#### Controller
Only one controller was implemented for the project purpose. 
* ```TaskExecutorController``` The controller is used to take the Job in json format and provide it to the service.
   
          
            "tasks": [
            {
                  "name": "task-1",
                  "command": "touch /tmp/file1"
            },
            {
                  "name": "task-3",
                  "command": "echo 'Hello World!' > /tmp/file1",
                  "requires": [
                        "task-1"
                  ]
            },
            {
                  "name": "task-4",
                  "command": "rm /tmp/file1",
                  "requires": [
                        "task-2",
                        "task-3"
                  ]
            },
            {
                  "name": "task-2",
                  "command": "cat /tmp/file1",
                  "requires": [
                        "task-3"
                  ]
            }
         ]

## Examples

#### When called from the appropriate REST client, it will return tasks ordered by precedence.
 ```jsx
/api/execute
```
Request body

```jsx

    {
        "tasks": [
        {
            "name": "task-1",
            "command": "touch /tmp/file1"
        },
        {
            "name": "task-3",
            "command": "echo 'Hello World!' > /tmp/file1",
            "requires": [
                "task-1"
            ]
        },

        {
            "name": "task-4",
            "command": "rm /tmp/file1",
            "requires": [
                "task-2",
                "task-3"
            ]
        },
        {
            "name": "task-2",
            "command": "cat /tmp/file1",
            "requires": [
                "task-3"
            ]
        }
        ]
    }

```

Response

```jsx
{
    [
        {
            "name": "task-1",
            "command": "touch /tmp/file1"
        },
        {
            "name": "task-3",
            "command": "echo 'Hello World!' > /tmp/file1"
        },
        {
            "name": "task-2",
            "command": "cat /tmp/file1"
        },
        {
            "name": "task-4",
            "command": "rm /tmp/file1"
        }
    ]
}
```
#### When called from the command line, as shown in the example below, it will execute tasks directly.
 ```jsx
curl -d @tasks.json -H "Content-Type: application/json" http://localhost:8080/api/execute | bash
```
Request body stored in the 'tasks.json' file

```jsx

    {
        "tasks": [
        {
            "name": "task-1",
            "command": "touch /tmp/file1"
        },
        {
            "name": "task-3",
            "command": "echo 'Hello World!' > /tmp/file1",
            "requires": [
                "task-1"
            ]
        },

        {
            "name": "task-4",
            "command": "rm /tmp/file1",
            "requires": [
                "task-2",
                "task-3"
            ]
        },
        {
            "name": "task-2",
            "command": "cat /tmp/file1",
            "requires": [
                "task-3"
            ]
        }
        ]
    }

```

Result from command execution.

```jsx
Hello World!
```