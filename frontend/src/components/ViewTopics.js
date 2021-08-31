import React from 'react';
import axios from 'axios';
import axiosRetry from 'axios-retry';
import { makeStyles } from '@material-ui/core/styles';
import { useHistory } from 'react-router';
import VisibilityRoundedIcon from '@material-ui/icons/VisibilityRounded';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

const REQUEST_URL = "http://localhost:8181/pharmacollab/topics/getTopicsByUserId?userId=Aditya123";


const ViewTopics = ((props) => {

    const useStyles = makeStyles(theme => ({
        root: {
            width: '100%',
            maxWidth: 360,
            backgroundColor: theme.palette.background.paper,
        },
        title: {
            [theme.breakpoints.down("xs")]: {
                flexGrow: 1
            }
        },
        listclass: {
            alignContent: 'center'

        },
        headerOptions: {
            display: "flex",
            flex: 1,
            justifyContent: "space-evenly"
        },
        div: {
            position: 'relative',
            marginRight: theme.spacing(10),
            marginLeft: theme.spacing(10),
            marginTop: theme.spacing(5),
            justifyContent: 'center',
        }
    }));

    const [topics, setTopics] = React.useState([]);
    let history = useHistory();
    const getTopics = () => {
        if (topics.length === 0) {
            axiosRetry(axios, { retries: 0 });

            axios.get(REQUEST_URL)
                .then((response) => {
                    console.log('response.data' + response.data);
                    setTopics(response.data);
                }, [topics])
                .catch(error => {
                    console.error("Unable to load data from endpoint:" + REQUEST_URL, error);
                    history.push('/oops');
                });
        }
    };

    React.useLayoutEffect(() => getTopics());
    const classes = useStyles();

    const handleTopicNavigation = (event, id) => {
        localStorage.setItem("topicId", id);
        history.push("/viewDashboard/" + id);
    }

    return (
        <div className={classes.div}>
            <h1 className={classes.headerOptions}>Assigned topics:</h1>
            <hr/>
            
            {topics.length > 0 && topics.map(function (topic, index) {
                return (
                    <List className={classes.listclass} key={topic.topicId+index} component="nav" aria-label="main mailbox folders">
                        <ListItem button id={topic.topicId} key={index} onClick={(e) => { handleTopicNavigation(e, topic.topicId) }}>
                            <ListItemIcon>
                                <VisibilityRoundedIcon />
                            </ListItemIcon>
                            <ListItemText key={topic.title} primary={'Title: ' + topic.title + ', Updated on ' + topic.updated} />
                        </ListItem>
                    </List>
                )
            })}
            
        </div>
    )
});

export default ViewTopics