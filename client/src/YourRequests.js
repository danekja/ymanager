import React, { useEffect } from 'react';
import './App.css';
import moment from 'moment';
import * as api_fetch from './api'

function YourRequests(props) {

  useEffect( () => {
      getData();
    }, []);

  // get requests from server
  const getData = async () => {

    api_fetch.loadYourRequests().then((data) => {

      props.setUser(data.map(request => {
        const a = request.date;
        const b = [a.slice(0, 4), "-", a.slice(5, 7), "-", a.slice(8, 10)].join('');

      return (
        {
          title: props.userName.name,
          id: request.id,
          start: moment(b).format("D.M.YYYY"),
          status: request.status = request.status.toLowerCase(),
          type: convertVacationType(request.type)
        }
      )
    }))
    }).catch(reason => {
      alert(reason)
    });
}

function convertVacationType(vacationType) {
  // vacationType =  'SICK_DAY' ? 'sickday' : 'holiday'

  // if (vacationType = 'SICK_DAY') {
  // return: 'sickday',
  // } else {
  //   return: 'holiday'
  // }

  switch (vacationType) {
    case 'SICK_DAY' :
      return 'sickday';
    case 'HOLIDAY':
      return 'holiday';
  }
}
  

  return (
    <div className="offs-request column">
      <h3>Your Requests</h3>
      <div className="underline-1"></div>
      <div className="offs-items column">
        <div className="offs-item row">
          <table>
            {props.userRequest.length > 0 ?
            
            <tbody>
              <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Date</th>    
                <th>Status</th>    
              </tr>
              {props.userRequest.map(user => (
                <tr key={user.id}>
                <td>{user.title}</td>
                <td>{user.type}</td>    
                <td>{user.end ? user.start + " - " + user.end : user.start}</td>
                <td>{user.status}</td>
              </tr>
              ))}
            </tbody>
            :
            <tbody>
              <div>No requests</div>
            </tbody>}
          </table>
        </div>
      </div>
    </div>
  )
}


export default YourRequests;