import React, { useEffect } from 'react';
import './App.css';

function YourRequests(props) {

  useEffect( () => {
      getData();
    }, []);

  // get requests from server
  const getData = async () => {
    try {
      const response = await fetch(
        'http://devcz.yoso.fi:8090/ymanager/user/6/calendar?from=2020/06/24&status=PENDING', {
          headers: {
            Authorization: 6
          },
        }
      );

    if (response.ok) {
      const data = await response.json();
      props.setUser(data.map(request => {
        const a = request.date;
        const b = [a.slice(0, 4), "-", a.slice(5, 7), "-", a.slice(8, 10)].join('');

      return (
        {
          title: props.userName.name,
          id: request.id,
          start: b,
          status: request.status,
          type: request.type
        }
      )
    }))
    } else {
      if(response.status === 400) {
        alert('error 400 GET DATA (YOUR REQUEST)')
     }
        else if (response.status === 500) {
           alert ('error 500 GET DATA (YOUR REQUEST)')
        }
        else {
           alert('error GET DATA (YOUR REQUEST)')
        }
    }
  } catch (e) {
    console.log(e)
    alert('error catch GET DATA (YOUR REQUEST)')
  }
}
        

  return (
    <div className="offs-request column">
      <h3>Your Requests</h3>
      <div className="underline-1"></div>
      <div className="offs-items column">
        <div className="offs-item row">
          <table>
            <tbody>
              <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Date</th>    
                <th>Status</th>    
              </tr>
              {props.userRequest.map(user => (
              <tr>
                <td>{user.title}</td>
                <td>{user.type}</td>    
                <td>{user.end ? user.start + " - " + user.end : user.start}</td>
                <td>{user.status}</td>
              </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}


export default YourRequests;