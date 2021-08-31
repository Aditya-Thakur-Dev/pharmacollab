import React from 'react';
import axios from 'axios';
import axiosRetry from 'axios-retry';
import { useReducer } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Button, Paper, Typography, TextField, Icon } from '@material-ui/core';
import { useHistory } from 'react-router';
import SendIcon from '@material-ui/icons/Send';
import AttachFileIcon from '@material-ui/icons/AttachFile';
import Select from '@material-ui/core/Select';
import NativeSelect from '@material-ui/core/NativeSelect';

const CreateOrUpdateJournalEntry = (props) => {

    const BASE_URL = "http://localhost:8181/pharmacollab/journals/createJournal";
    const UPDATE_URL = "http://localhost:8181/pharmacollab/journals/updateJournal";
    const JOURNAL_DETAILS_URL = "http://localhost:8181/pharmacollab/sampleRun/find/journalById?journalId=";
    const history = useHistory();

    const useStyles = makeStyles((theme) => ({
        button: {
            margin: theme.spacing(1)
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
        div: {
            position: 'relative',
            marginRight: theme.spacing(10),
            marginLeft: theme.spacing(10),
            marginTop: theme.spacing(5),
            justifyContent: 'center',
            display: "flex",
            flexWrap: "wrap"
        }
    }));

    const classes = useStyles();
    const [formInput, setFormInput] = useReducer(
        (state, newState) => ({ ...state, ...newState }),
        {
            journalId: null,
            topicId: localStorage.getItem("topicId"),
            userId: localStorage.getItem("userId"),
            journalName: "",
            type: "",
            "exploratoryDiscovery": null,
            laterStageDiscovery: null,
            preclinicalTrial: null
            // "exploratoryDiscovery": {"exploratoryDiscoveryId": "",journalId: null, diseaseOperationFile: "",
            // targetFile: "", hypothesisFile: ""},
            // laterStageDiscovery: {laterStageDiscoveryId: "", journalId: "", compoundsFile: "",
            // fitForTarget: "", status: ""},
            // preclinicalTrial: {trialId: "",journalId:"", sampleId: "", acceptanceCriteriaFile: "", 
            // trialMethod: "", result: "", status: ""}
        }
    );

    const [urlParamId, seturlParamId] = React.useState(props.match.params.id);
    
    const getJournalData = () => {
        if (urlParamId > 0) {
            axiosRetry(axios, { retries: 0 });
            

            // axios.get(JOURNAL_DETAILS_URL + localStorage.getItem("journalId"))
            //     .then((response) => {
            //         console.log('response.data' + response.data);
            //         console.log(JSON.push.stringify(response.data));
            //         setjournalDetails(response.data);
            //     }, [journalDetails])
            //     .catch(error => {
            //         console.error("Unable to load data from endpoint:" + REQUEST_URL, error);
            //         history.push('/oops');
            //     });
        }
    };


    React.useLayoutEffect(() => getJournalData());

    const handleSubmit = (evt) => {
        evt.preventDefault();
        let data = { formInput };
        axiosRetry(axios, { retries: 0 });
        // if(localStorage.getItem("journalId") === '') {
            axios.post(BASE_URL, JSON.stringify(data.formInput), {
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => history.push("/viewTopic"))
                .catch(error => {
                    console.error("Unable to load data from endpoint:" + BASE_URL, error);
                    history.push('/oops');
                });
        // } else {
        //     axios.post(UPDATE_URL, JSON.stringify(data.formInput), {
        //         headers: {
        //             'Content-Type': 'application/json'
        //         }
        //     }).then(response => history.push("/viewTopic"))
        //         .catch(error => {
        //             console.error("Unable to load data from endpoint:" + BASE_URL, error);
        //             history.push('/oops');
        //         });
        // }
        
    };

    const handleInput = evt => {
        const field = evt.target.name;
        const newValue = evt.target.value;
        setFormInput({ [field]: newValue });
    };
    
    const journalTypes = [{
        enum : 'EXPLORATORY_DISCOVERY',
        text: 'Exploratory Discovery'
    }, {
        enum : 'LATER_STAGE_DISCOVERY',
        text: 'Later Stage Discovery'
    }, {
        enum : 'PRECLINICAL_TRIAL',
        text: 'Preclinical Trial'
    }
]


    return (
        <div className={classes.div}>
            <Paper className={classes.root}>
                <Typography variant="h5" component="h3">
                    {props.formName}
                </Typography>
                <Typography component="p">{props.formDescription}</Typography>

                <form onSubmit={handleSubmit}>
                <TextField
                        label="User ID"
                        id="margin-normal"
                        name="exploratoryDiscovery.userId"
                        defaultValue={localStorage.getItem("userId")}
                        className={classes.textField}
                        disabled={true}
                        helperText="An ID to identify you"
                        onChange={handleInput}
                        variant="filled"
                    />
                    <TextField
                        label="Journal ID"
                        id="margin-normal"
                        name="journalId"
                        disabled={true}
                        defaultValue={localStorage.getItem("journalId")}
                        className={classes.textField}
                        helperText="Will be autogenerated for new entries"
                        onChange={handleInput}
                        variant="filled"
                    />
                    <TextField
                        label="Topic ID"
                        id="margin-normal"
                        name="topicId"
                        defaultValue={localStorage.getItem("topicId")}
                        className={classes.textField}
                        disabled={true}
                        helperText="Need not worry about this field"
                        onChange={handleInput}
                        variant="filled"
                    />
                    <TextField
                        label="Exploratory Journal ID"
                        id="margin-normal"
                        name="exploratoryDiscovery.journalId"
                        // defaultValue={formInput.exploratoryDiscovery.journalId}
                        defaultValue={''}
                        className={classes.textField}
                        disabled={true}
                        helperText="Will be autogenerated for new entries"
                        variant="filled"
                        // onChange={handleInput}
                    />
                    <TextField
                        label="Exploratory Discovery ID"
                        id="margin-normal"
                        name="exploratoryDiscovery.exploratoryDiscoveryId"
                        // defaultValue={formInput.exploratoryDiscovery.exploratoryDiscoveryId}
                        defaultValue={localStorage.getItem("exploratoryDiscoveryId")}
                        className={classes.textField}
                        disabled={true}
                        variant="filled"
                        helperText="Need not worry about this field"
                        // onChange={handleInput}
                    />
                    <TextField
                        label="Journal name"
                        id="margin-normal"
                        name="journalName"
                        defaultValue={localStorage.getItem("journalName")}
                        className={classes.textField}
                        helperText="Set a nice identifiable name for this journal entry"
                        onChange={handleInput}
                    />
                    <TextField
                        label="Exploratory Discovery Journal ID"
                        id="margin-normal"
                        name="exploratoryDiscovery.journalId"
                        // defaultValue={formInput.journalId}
                        defaultValue={localStorage.getItem("exploratoryDiscoveryJournalId")}
                        variant="filled"
                        className={classes.textField}
                        helperText="Will be autogenerated later"
                        // onChange={handleInput}
                    />
                    <NativeSelect
                        className= {classes.textField}
                        value={formInput.type}
                        onChange={handleInput}
                        inputProps={{
                            name: 'type',
                          }}
                    >
                        <option aria-label="None" value="Journal Entry Type"/>
                        {journalTypes.map(journalType => {
                            return <option key={journalType.enum} value={journalType.enum}>{journalType.text}</option>
                        })}
                    </NativeSelect>

                    <TextField
                        label="Status"
                        id="margin-normal"
                        name="status"
                        defaultValue={formInput.status}
                        className={classes.textField}
                        disabled={true}
                        helperText="No permission to edit this field"
                        onChange={handleInput}
                        variant="filled"
                    />
                    <AttachFileIcon/><TextField
                        label="Disease operation file"
                        id="margin-normal"
                        name="exploratoryDiscovery.diseaseOperationFile"
                        // defaultValue={formInput.exploratoryDiscovery.diseaseOperationFile}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Attach disease operation file"
                        // onChange={handleInput}
                    />
                    <AttachFileIcon/><TextField
                        label="Hypothesis file"
                        id="margin-normal"
                        name="exploratoryDiscovery.hypothesisFile"
                        // defaultValue={formInput.exploratoryDiscovery.hypothesisFile}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Attach your hypothesis"
                        // onChange={handleInput}
                    />
                    <AttachFileIcon/><TextField
                        label="Target file"
                        id="margin-normal"
                        name="exploratoryDiscovery.targetFile"
                        // defaultValue={formInput.exploratoryDiscovery.targetFile}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Attach Target file"
                        // onChange={handleInput}
                        variant="filled"
                    />
                    
                    <TextField
                        label="Later Stage Discovery ID"
                        id="margin-normal"
                        name="laterStageDiscovery.laterStageDiscoveryId"
                        // defaultValue={formInput.laterStageDiscovery.laterStageDiscoveryId}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Need not worry about this field"
                        // onChange={handleInput}
                        variant="filled"
                    />
                    <TextField
                        label="Target file"
                        id="margin-normal"
                        name="laterStageDiscovery.journalId"
                        // defaultValue={formInput.journalId}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Will be autogenerated later"
                        // onChange={handleInput}
                    />
                    <AttachFileIcon/><TextField
                        label="Molecule Compounds file"
                        id="margin-normal"
                        name="laterStageDiscovery.compoundsFile"
                        // defaultValue={formInput.laterStageDiscovery.compoundsFile}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Attach Target file"
                        // onChange={handleInput}
                    />
                    <TextField
                        label="Fit for target"
                        id="margin-normal"
                        name="laterStageDiscovery.fitForTarget"
                        // defaultValue={formInput.laterStageDiscovery.fitForTarget}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Will be boolean later"
                        // onChange={handleInput}
                    />
                    <TextField
                        label="Status"
                        id="margin-normal"
                        disabled={true}
                        name="laterStageDiscovery.status"
                        // defaultValue={formInput.laterStageDiscovery.status}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Later Stage Discovery Status"
                        // onChange={handleInput}
                    />
                    


                    <TextField
                        label="Preclinical Trial ID"
                        id="margin-normal"
                        name="preclinicalTrial.trialId"
                        // defaultValue={formInput.preclinicalTrial.trialId}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Sequence of Trial will be autogenerated later"
                        // onChange={handleInput}
                    />
                    <TextField
                        label="Journal ID"
                        id="margin-normal"
                        name="preclinicalTrial.journalId"
                        // defaultValue={formInput.journalId}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Will be autogenerated later"
                        // onChange={handleInput}
                    />
                    <TextField
                        label="Test sample ID"
                        id="margin-normal"
                        name="preclinicalTrial.sampleId"
                        // defaultValue={formInput.preclinicalTrial.sampleId}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Barcode number etc"
                        // onChange={handleInput}
                    />
                    <AttachFileIcon/><TextField
                        label="Acceptance criteria file"
                        id="margin-normal"
                        name="preclinicalTrial.acceptanceCriteriaFile"
                        // defaultValue={formInput.preclinicalTrial.acceptanceCriteriaFile}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Given..When..Then..Cases"
                        // onChange={handleInput}
                    />
                    <TextField
                        label="Method chosen for preclinical trial"
                        id="margin-normal"
                        name="preclinicalTrial.trialMethod"
                        // defaultValue={formInput.preclinicalTrial.trialMethod}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="IVF, Nasal, Injected etc"
                        // onChange={handleInput}
                    />
                    <TextField
                        label="How did the preclinical trial go ?"
                        id="margin-normal"
                        name="preclinicalTrial.result"
                        // defaultValue={formInput.preclinicalTrial.result}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="MANDATORY"
                        // onChange={handleInput}
                    />
                    <TextField
                        label="Status"
                        id="margin-normal"
                        disabled={true}
                        name="preclinicalTrial.status"
                        // defaultValue={formInput.preclinicalTrial.status}
                        defaultValue={''}
                        className={classes.textField}
                        helperText="Status of Preclinical Trial"
                        // onChange={handleInput}
                    />
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        className={classes.button}
                    >
                        Save <SendIcon className={classes.rightIcon}/>
                    </Button>
                </form>
            </Paper>
        </div>
    )
}

export default CreateOrUpdateJournalEntry
