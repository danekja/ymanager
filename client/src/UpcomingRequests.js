import React, { useEffect } from 'react';
import './App.css';


function UpcomingRequests(props) {

  useEffect( () => {
    getData();
  }, []);

  // get requests from server
  const getData = async () => {
    try {
    const response = await fetch(
      'http://devcz.yoso.fi:8090/ymanager/users/requests/vacation?status=PENDING', {
        headers: {
          Authorization: 1
        }
      },
    );

     if (response.ok) {
    const data = await response.json();
    props.setUser(data.map(request => {
      
      const a = request.date;
      const b = [a.slice(0, 4), "-", a.slice(5, 7), "-", a.slice(8, 10)].join('');

      return (
        {
          title: request.firstName + ' ' + request.lastName,
          id: request.id,
          type: request.type,
          start: b,
          end: null,
          status: request.status
      })
    }))
  } else {
    if(response.status === 400) {
      alert('error 400 GET DATA (UPCOMING REQUESTS)')
   }
      else if (response.status === 500) {
         alert ('error 500 GET DATA (UPCOMING REQUESTS)')
      }
      else {
         alert('error GET DATA (UPCOMING REQUESTS)')
      }
  }
} catch (e) {
  console.log(e)
  alert('error catch GET DATA (UPCOMING REQUESTS)')
  }    
}

  // send accepted request to server
  const acceptRequest = async (user) => {
    try {
    const response = await fetch('http://devcz.yoso.fi:8090/ymanager/user/requests?type=VACATION', {
      headers: {
        Authorization: 1,
        'Content-Type': 'application/json',
      },
      method: 'PUT',
      body: JSON.stringify({
        id: user.id,
        status: 'ACCEPTED',
      }),
    });

    if (response.ok) {
    const userProps = {
      title: user.title,
      id: 0,
      type: user.type, 
      start: user.start
  }
  //concat new request to current ones
      props.setRequest((acceptedRequest) => acceptedRequest.concat(userProps))
  //request accept button
      props.setUser((pendingRequest) => pendingRequest.filter((item) => item !== user));

    } else {
      if(response.status === 400) {
        alert('error 400 SEND ACCEPTED DATA (UPCOMING REQUESTS)')
     }
        else if (response.status === 500) {
           alert ('error 500 SEND ACCEPTED DATA (UPCOMING REQUESTS)')
        }
        else {
           alert('error SEND ACCEPTED DATA (UPCOMING REQUESTS)')
        }
    }
  } catch (e) {
    alert('error catch SEND ACCEPTED DATA (UPCOMING REQUESTS)')
    }
  }

  //send rejected request to server
  const declineRequest = async (user) => {
    try {
      const response = await fetch('http://devcz.yoso.fi:8090/ymanager/user/requests?type=VACATION', {
        headers: {
          Authorization: 1,
          'Content-Type': 'application/json',
        },
        method: 'PUT',
        body: JSON.stringify({
          id: user.id,
          status: 'REJECTED',
        }),
      });

    if (response.ok) {    
  //request cancel button
      props.setUser((acceptedRequest) => acceptedRequest.filter((item) => item !== user))

    } else {
      if(response.status === 400) {
        alert('error 400 SEND REJECTED DATA (UPCOMING REQUESTS)')
     }
        else if (response.status === 500) {
           alert ('error 500 SEND REJECTED DATA (UPCOMING REQUESTS)')
        }
        else {
           alert('error SEND REJECTED DATA (UPCOMING REQUESTS)')
        }
    }
  } catch (e) {
    console.log(e)
    alert('error catch SEND REJECTED DATA (UPCOMING REQUESTS)')
    }
  }

  return (
    <div className="offs-request column">
      <h3>New Requests</h3>
      <div className="underline-1"></div>
      <div className="offs-items column">
        <div className="offs-item row">
          <table>
            <tbody>
              <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Date</th>    
              </tr>
              {props.userRequest.map(user => (
              <tr>
                <td>{user.title}</td>
                <td>{user.type}</td>    
                <td>{user.end ? user.start + " - " + user.end : user.start}</td>
                <div className="offs-btn row">
                  <button onClick={() => acceptRequest(user)} type="submit" className="btn btn-submit">Accept</button>
                  <button onClick={() => declineRequest(user)} type="submit" className="btn btn-cancel">Decline</button>     
              </div>
              </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
    )
  }

export default UpcomingRequests;

     


        // return (shouldRemoveThisItem === true) ? false : true;
        //})
      //)
      // props.setUser((pendingRequest) => pendingRequest.filter(
        
      //   function(item) {
      //   const shouldRemoveThisItem = item === user;
        
      //   if (shouldRemoveThisItem === true) {
      //     return false;
      //   } else {
      //     return true;
      //   }})
      // )