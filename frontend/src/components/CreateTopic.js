import React from 'react'
import axios from 'axios'
import { useState, useReducer } from 'react';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import { Button, Paper, Typography, TextField, Icon, TextareaAutosize } from '@material-ui/core'
import { useHistory } from 'react-router';


const CreateTopic = (props) => {

    const BASE_URL = "http://localhost:8181/pharmacollab/topics/createNewTopic";
    const history = useHistory();

    const useStyles = makeStyles((theme) => ({
        button: {
            margin: theme.spacing(1),
            display: 'block',
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
            display:'flex',
            textAlign: 'center',
            padding: theme.spacing(3, 2),
            margin:'auto',
            justifyContent:'center'
        },
        container: {
            display: "block",
            flexWrap: "wrap"
        },
        textField: {
            marginLeft: theme.spacing(1),
            marginRight: theme.spacing(1),
            width: 400
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

    const [sampleRun, setSampleRun] = useReducer(
        (state, newState) => ({ ...state, ...newState }),
        {           
            created:"",
            departmentId:"",
            diseaseId:"",
            shortDescription: "",
            status:"",
            title: "",
            topicId:"",
            updated:"",
        }
    );

    const handleSubmit = (evt) => {
        evt.preventDefault();
        let data = { sampleRun };
        axios.post(BASE_URL, JSON.stringify(data.sampleRun), {
            headers: {
                        'Content-Type': 'application/json'
                    }
        }).then(response => history.push("/assignTopic"))
        .catch(error => console.error("Error:", error));
    };

    const handleInput = evt => {
        const field = evt.target.name;
        const newValue = evt.target.value;
        setSampleRun({ [field]: newValue });
    };

    const classes = useStyles();
    return (
        <div className={classes.div}>
            <Paper className={classes.root}>
                <Typography variant="h5" component="h3">
                    {props.formName}
                </Typography>
                <Typography component="p">{props.formDescription}</Typography>

                <form onSubmit={handleSubmit}>
                    <TextField
                        label="Topic"
                        id="margin-normal"
                        name="title"
                        defaultValue={sampleRun.title}
                        className={classes.textField}
                        helperText="Enter the name of the topic"
                        onChange={handleInput}
                    />
                    <TextField
                        label="Topic Description"
                        id="margin-normal"
                        name="shortDescription"
                        multiline
                        defaultValue={sampleRun.shortDescription}
                        className={classes.textField}
                        helperText="Enter Description"
                        onChange={handleInput}
                    />
                        <TextField 
                        label="Department Id"
                        id="margin-normal"
                        name="departmentId"
                        defaultValue={sampleRun.departmentId}
                        className={classes.textField}
                        helperText="Enter Department ID"
                        onChange={handleInput}
                        />
                        <TextField 
                        label="Disease Id"
                        id="margin-normal"
                        name="diseaseId"
                        defaultValue={sampleRun.diseaseId}
                        className={classes.textField}
                        helperText="Enter disease ID"
                        onChange={handleInput}
                        />
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

export default CreateTopic
