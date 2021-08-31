import {slide as Menu} from 'react-burger-menu'
import './../resources/SideMenu.css'
import { BrowserRouter as Router, Switch, Route , Link} from 'react-router-dom' 
import Discovery from './Discovery'
import Trial from './Trial'
import ViewDashboard from './ViewDashboard'
import CreateOrUpdate from './CreateOrUpdateJournalEntry'

const SideMenu = ({pageWrapId, outerContainerId, name}) => {
    const routes=[{
        path: '/discovery',
        component: Discovery,
    }, {
        path: '/trial',
        component: Trial,
    }, {
        path: '/viewDashboard',
        component: ViewDashboard,
    }, {
      path: '/journalEntry/:id',
      component: CreateOrUpdate,
    }
]
    return (
        <Router>
        <Menu pageWrapId={pageWrapId} outerContainerId={outerContainerId}>
        <h1 style={{color: '#a90000'}}>{name}</h1>
        <p className='user'>user id: abcd</p>
        <Link className="item-list" to="/viewDashboard">
        <ul style={{listStyleType: 'none'}}> <li>Dashboard</li></ul>
        </Link>
          <ul className="menu-item" style={{listStyleType: 'none'}}> <li id="visible">Topic
          <ul id="hidden">
            <li><Link className="menu-item" to="/discovery">Create topic</Link></li>
            <li><Link className="menu-item" to="/discovery">Allocate topic</Link></li>
            <li><Link className="menu-item" to="/discovery">My topics</Link></li>
            </ul>
          </li></ul>
        <Link className="menu-item" to="/trial">
          Trial
        </Link>
      </Menu>
      <Switch>
        {routes.map((route, index)=> (
            <Route
            key={index}
            path={route.path}
            component={route.component}
            />
        ))}
      </Switch>
      </Router>
    )
}

export default SideMenu