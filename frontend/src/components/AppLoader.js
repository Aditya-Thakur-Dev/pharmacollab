import React from 'react'
import { Switch, Route} from 'react-router-dom' 
import Discovery from './Discovery'
import ViewDashboard from './ViewDashboard'
import ViewTopics from './ViewTopics'
import CreateOrUpdate from './CreateOrUpdateJournalEntry'
import Home from './Home';
import ErrorPage from './Error';
import CreateTopic from './CreateTopic';
import AssignTopic from './AssignTopic';

const AppLoader = ({pageWrapId, outerContainerId, name}) => {
    const routes=[{
        path: '/',
        component: Home,
    }, {
        path: '/discovery',
        component: Discovery,
    }, {
        path: '/createTopic',
        component: CreateTopic
    }, {
        path: '/assignTopic',
        component: AssignTopic
    }, {
        path: '/viewTopic',
        component: ViewTopics,
    }, {
        path: '/viewDashboard/:id',
        component: ViewDashboard,
    }, {
      path: '/journalEntry/:id',
      component: CreateOrUpdate,
    }, {
        path: '/oops',
        component: ErrorPage
    }
]
    return (
        <div>
            <Switch>
        {routes.map((route, index)=> (
            <Route exact
            key={index}
            path={route.path}
            component={route.component}
            />
        ))}
      </Switch>
        </div>
    )
}

export default AppLoader
