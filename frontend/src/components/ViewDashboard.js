import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import FirstPageIcon from '@material-ui/icons/FirstPage';
import KeyboardArrowLeft from '@material-ui/icons/KeyboardArrowLeft';
import KeyboardArrowRight from '@material-ui/icons/KeyboardArrowRight';
import LastPageIcon from '@material-ui/icons/LastPage';
import EditIcon from '@material-ui/icons/Edit';
import AddToQueueIcon from '@material-ui/icons/AddToQueue';
import axios from 'axios';
import axiosRetry from 'axios-retry';
import { useState } from 'react';
import {
    Button, Paper, Table, TableBody, TableCell, TableContainer,
    TableFooter, TablePagination, TableRow, IconButton
} from '@material-ui/core'
import { useHistory } from 'react-router';

const BASE_URL = "http://localhost:8181/pharmacollab/journals/getJournalsByTopicIdAndUserId?topicId=";

//page navigation styling
const pageNavigationControlStyle = makeStyles((theme) => ({
    root: {
        flexShrink: 0,
        marginLeft: theme.spacing(2.5),
        flexWrap: 'wrap',
        alignContent: 'center',
    },
}));

//table styling
const tableStyle = makeStyles((theme) => ({
    table: {
        minWidth: 500,
        alignContent: 'center',
    },
}));

const journalDataContainerStyle = makeStyles((theme) => ({
    div: {
        position: 'relative',
        marginRight: theme.spacing(10),
        marginLeft: theme.spacing(10),
        marginTop: theme.spacing(5),
        display: 'flex',
        justifyContent: 'center',
    }

}))


//service functionality for page navigation
function TablePaginationActions(props) {
    const classes = pageNavigationControlStyle();
    const theme = useTheme();
    const { count, page, rowsPerPage, onPageChange } = props;

    const handleFirstPageButtonClick = (event) => {
        onPageChange(event, 0);
    };

    const handleBackButtonClick = (event) => {
        onPageChange(event, page - 1);
    };

    const handleNextButtonClick = (event) => {
        onPageChange(event, page + 1);
    };

    const handleLastPageButtonClick = (event) => {
        onPageChange(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
    };

    //render page navigation controls
    return (
        <div className={classes.root}>

            <IconButton
                onClick={handleFirstPageButtonClick}
                disabled={page === 0}
                aria-label="first page"
            >
                {theme.direction === 'rtl' ? <LastPageIcon /> : <FirstPageIcon />}
            </IconButton>
            <IconButton onClick={handleBackButtonClick} disabled={page === 0} aria-label="previous page">
                {theme.direction === 'rtl' ? <KeyboardArrowRight /> : <KeyboardArrowLeft />}
            </IconButton>
            <IconButton
                onClick={handleNextButtonClick}
                disabled={page >= Math.ceil(count / rowsPerPage) - 1}
                aria-label="next page"
            >
                {theme.direction === 'rtl' ? <KeyboardArrowLeft /> : <KeyboardArrowRight />}
            </IconButton>
            <IconButton
                onClick={handleLastPageButtonClick}
                disabled={page >= Math.ceil(count / rowsPerPage) - 1}
                aria-label="last page"
            >
                {theme.direction === 'rtl' ? <FirstPageIcon /> : <LastPageIcon />}
            </IconButton>
        </div>
    );
}

TablePaginationActions.propTypes = {
    count: PropTypes.number.isRequired,
    onPageChange: PropTypes.func.isRequired,
    page: PropTypes.number.isRequired,
    rowsPerPage: PropTypes.number.isRequired,
};


export default function CustomPaginationActionsTable(props) {
    const [rows, setRows] = useState([]);
    const classes = tableStyle();
    const divClass = journalDataContainerStyle();

    const history = useHistory();
    const [page, setPage] = React.useState(0);
    const storedUser = localStorage.getItem('user');

    console.log(JSON.parse(storedUser));
    console.log(JSON.parse(storedUser).userId);
    const [callCounter, setCallCounter] = useState(0);

    const getData = () => {
        if (callCounter === 0) {
            axiosRetry(axios, { retries: 1 });
            console.log(localStorage.getItem("userId"));
            axios.get(BASE_URL + localStorage.getItem("topicId") + '&userId=Aditya123')
            // axios.get(BASE_URL + localStorage.getItem("topicId") + '&userId=' + localStorage.getItem("userId"))
                .then((response) => {
                    console.log(response.data);
                    console.log("response data length" + response.data.length);
                    if (response.data.length > 0) {
                        setRows(response.data);
                    } else {
                        setRows([]);
                    }
                    setCallCounter(1);
                }, [rows, callCounter])
                .catch(error => {
                    console.error("Unable to load data from endpoint:" + BASE_URL, error);
                    setRows([]);
                    history.push('/oops');
                }, [rows]);
        }
    };

    React.useLayoutEffect(() => getData());


    const [rowsPerPage, setRowsPerPage] = React.useState(3);

    const emptyRows = rowsPerPage - Math.min(rowsPerPage, rows.length - page * rowsPerPage);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleEdit = (event, id, type, journal) => {
        localStorage.setItem("journalId", journal.journalId);
        localStorage.setItem("journalType", type);
        localStorage.setItem("exploratoryDiscoveryJournalId", journal.exploratoryDiscovery.journalId);
        localStorage.setItem("exploratoryDiscoveryId", journal.exploratoryDiscovery.exploratoryDiscoveryId);
        localStorage.setItem("journalName", journal.journalName);
        localStorage.setItem("journal", journal);
        history.push("/journalEntry/" + journal.journalId)
    }

    const handleNewJournalEntry = (event) => {
        localStorage.setItem("journalId", '');
        localStorage.setItem("journalType", '');
        localStorage.setItem("exploratoryDiscoveryId", '');
        localStorage.setItem("journalName", '');
        localStorage.setItem("journal", {});
        history.push("/journalEntry/0");
    }

    //render page upon navigation
    return (
        <div className={divClass.div}>
            <Button
                type="submit"
                variant="contained"
                color="primary"
                className={classes.button}
                onClick={(e) => { handleNewJournalEntry(e) }}
            >
                <AddToQueueIcon className={classes.rightIcon} />
            </Button>
            <TableContainer component={Paper}>
                <Table className={classes.table} aria-label="custom pagination table">
                    <TableBody>
                        {(rowsPerPage > 0
                            ? rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                            : rows
                        ).map((row) => (
                            <TableRow key={row.journalId}>
                                <TableCell style={{ width: 60 }} scope="row">
                                    <p>{row.topicId} : {row.type}</p>
                                    <p>{row.journalName}</p>
                                    <Button
                                        type="submit"
                                        variant="contained"
                                        color="primary"
                                        className={classes.button}
                                        onClick={(e) => { handleEdit(e, row.journalId, row.type, row) }}
                                    >
                                        <EditIcon className={classes.rightIcon} />
                                    </Button>
                                </TableCell>
                                {/* <TableCell style={{ width: 160 }} align="right">
                                
                                
                            </TableCell> */}
                            </TableRow>
                        ))}

                        {emptyRows > 0 && (
                            <TableRow style={{ height: 53 * emptyRows }}>
                                <TableCell colSpan={6} />
                            </TableRow>
                        )}
                    </TableBody>
                    <TableFooter>
                        <TableRow>
                            <TablePagination
                                style={{ display: 'flex' }}
                                rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
                                colSpan={1}
                                count={rows.length}
                                rowsPerPage={rowsPerPage}
                                page={page}
                                SelectProps={{
                                    inputProps: { 'aria-label': 'rows per page' },
                                    native: true,
                                }}
                                onPageChange={handleChangePage}
                                onRowsPerPageChange={handleChangeRowsPerPage}
                                ActionsComponent={TablePaginationActions}
                            />
                        </TableRow>
                    </TableFooter>
                </Table>
            </TableContainer>
        </div>
    );
}