import React from 'react'
import axios from 'axios'
import { useReducer } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Button, Paper, Typography, TextField, Icon, Select} from '@material-ui/core'
import { useHistory } from 'react-router';
import { NativeSelect } from '@material-ui/core';
import { FormHelperText } from '@material-ui/core';
import { InputLabel } from '@material-ui/core';
import { FormControl } from '@material-ui/core';

const AssignTopic = (props) => {

    const BASE_URL_ALLOCATE_TOPIC = "http://localhost:8181/pharmacollab/topics/allocateAndStartTopic";
    const BASE_URL_GET_USERS = "http://localhost:8181/pharmacollab/users/getCollaboratorsByAdminUserId?adminUserId="+"Mayu123";
    const BASE_URL_GET_TOPICS = "http://localhost:8181/pharmacollab/topics/getTopicsByAdminUserId?userId="+"Mayu123";
    const history = useHistory();

    const useStyles = makeStyles((theme) => ({
        button: {
            margin: theme.spacing(1),
            display:'block',
            margin:'auto',
            justifyContent:'center'
        },
        leftIcon: {
            marginRight: theme.spacing(1)
        },
        rightIcon: {
            marginLeft: theme.spacing(1)
        },
        iconSmall: {
            fontSize: 20
        },
        root: {
            padding: theme.spacing(3, 2)
        },
        container: {
            display: "flex",
            flexWrap: "wrap"
        },
        textField: {
            marginLeft: theme.spacing(1),
            marginRight: theme.spacing(1),
            width: 400
        },
        select: {
            marginLeft: theme.spacing(1),
            marginRight: theme.spacing(1),
            margin: theme.spacing(5),
            width: 400,
        }, 
        div: {
            position: 'relative',
        marginRight: theme.spacing(10),
        marginLeft: theme.spacing(10),
        marginTop: theme.spacing(5),
        display: 'flex',
        justifyContent: 'center',
        }
    }));

    const[user, setUser] = React.useState([]);
    const[topic, setTopic] = React.useState([]);



    React.useEffect(() => {
        const getUsers = () => {
            axios.get(BASE_URL_GET_USERS)
                .then((response) => {
                    console.log(response.data);
                    // console.log(response.data[0]);
                    setUser(response.data);
                });
        };
    getUsers()}, []);


    React.useEffect(() => {
        const getTopics = () => {
            axios.get(BASE_URL_GET_TOPICS)
                .then((response) => {
                    console.log(response.data);
                    console.log("response data length" + response.data.length);
                    setTopic(response.data);     
                });
        };
    getTopics()}, []);

    const classes = useStyles();
    const [formInput, setFormInput] = useReducer(
        (state, newState) => ({ ...state, ...newState }),
        {
            topicId: "",
            topicRole:[],
            userId: "",

        }
    );

    const handleSubmit = (evt) => {
        evt.preventDefault();
        let data = { formInput };
        console.log(data.formInput)
        console.log(JSON.stringify(data.formInput))
        axios.post(BASE_URL_ALLOCATE_TOPIC, [data.formInput], {
            headers: {
                        'Content-Type': 'application/json'
                    }
        }).then(response => history.push("/createTopic"))
        .catch(error => console.error("Error:", error));
    };

    const handleInput = evt => {
        const field = evt.target.name;
        let newValue = evt.target.value;
        if(field==='topicRole'){
             newValue = [evt.target.value]
        }
        
        console.log(newValue)
        setFormInput({ [field]: newValue });
    };

    const roles = ["Collaborator", "Reviewer"];
    return (
        <div className={classes.div}>
            <Paper className={classes.root}>
                <Typography variant="h5" component="h3">
                    {props.formName}
                </Typography>
                <Typography component="p">{props.formDescription}</Typography>

                <form onSubmit={handleSubmit}>
                <FormControl className= {classes.Select}>
                    <NativeSelect
                        className= {classes.textField}
                        value={formInput.userId}
                        onChange={handleInput}
                        inputProps={{
                            name: 'userId',
                          }}
                    >
                        <option aria-label="None"value="select Username"/>
                        {user.map(use => {
                            return <option key={use.userId} value={use.userId}>{use.userName}</option>
                        })}
                    </NativeSelect>
                    <FormHelperText>Select User</FormHelperText>
                    </FormControl>
                    <FormControl className= {classes.Select}>
                    <NativeSelect
                        className= {classes.textField}
                        value={formInput.topicId}
                        onChange={handleInput}
                        inputProps={{
                            name: 'topicId',
                          }}
                    >
                        <option aria-label="None" value=""/>
                        {topic.map(top => {
                            return <option key={top.topicId} value={top.topicId}>{top.title}</option>
                        })}
                    </NativeSelect>
                    <FormHelperText>Select Topic</FormHelperText>
                    </FormControl>
                    <FormControl className= {classes.Select}>
                    <NativeSelect
                        className= {classes.textField}
                        value={formInput.topicRole}
                        onChange={handleInput}
                        inputProps={{
                            name: 'topicRole',
                          }}
                    >
                        <option aria-label="None" value=""/>
                        {roles.map(role => {
                            return <option key={role} value={role}>{role}</option>
                        })}
                    </NativeSelect>
                    <FormHelperText>Select Role</FormHelperText>
                    </FormControl>
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        className={classes.button}
                    >
                        Save 
                        {/* <Icon className={classes.rightIcon}>send</Icon> */}
                    </Button>
                </form>
            </Paper>
        </div>
    )
}

export default AssignTopic
