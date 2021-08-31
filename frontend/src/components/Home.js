import React from 'react'
import { makeStyles } from '@material-ui/core/styles';

const Home = props => {
    const useStyles = makeStyles((theme) =>({
        div : {
            position: 'relative',
            marginRight: theme.spacing(10),
            marginLeft: theme.spacing(10),
            marginTop: theme.spacing(5),
        }}));

        const classes = useStyles();
    return (
        <div className={classes.div}>
            <p>Research and development operations require co-located interactions and collaboration amongst researchers. However, co-located work has been compromised due to ongoing pandemic situation and workplace restrictions.</p>

            <p>As a solution to manage safety of researchers, remote collaboration and continuity of operations, an online platform is need of the hour which also expedites the end-to-end workflow and ensures research quality.</p>
            <p>This platform should also have capability to derive effectiveness and worthiness of the research by having a monitoring system within end-to-end workflow for quality assurance.</p>
        </div>
    )
}
export default Home