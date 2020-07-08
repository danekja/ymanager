import React, { useState, useEffect } from 'react';
import './App.css';
import moment from 'moment';
import * as api_fetch from './api'

function UpcomingRequests(props) {

  useEffect( () => {
    getData();
  }, []);

  // get requests from server
  const getData = async () => {
   
    api_fetch.loadAdminRequests().then((data) => {
      props.setUser(data.map(request => {
      
        const a = request.date;
        const b = [a.slice(0, 4), "-", a.slice(5, 7), "-", a.slice(8, 10)].join('');
  
        return (
          {
            title: request.firstName + ' ' + request.lastName,
            id: request.id,
            type: convertVacationType(request.type),
            start: moment(b).format("D.M.YYYY"),
            end: null,
            status: request.status = request.status.toLowerCase()
        })
      }))
    }).catch(reason => {
      alert(reason)
    });
}

function convertVacationType(vacationType) {
  switch (vacationType) {
    case 'SICK_DAY' :
      return 'sickday';
    case 'HOLIDAY':
      return 'holiday';
  }
}



  // send accepted request to server
  const acceptRequest = async (user) => {
    try {

      const acceptedRequests = {
        id: user.id,
        status: 'ACCEPTED',
      }
    
      await api_fetch.sendAcceptedRequest(acceptedRequests).then(() => {
        
        const userProps = {
              title: user.title,
              id: 0,
              type: user.type, 
          type: user.type, 
              type: user.type, 
              start: user.start
        }
        //concat new request to current ones
          props.setRequest((acceptedRequest) => acceptedRequest.concat(userProps))
        //request accept button
          props.setUser((pendingRequest) => pendingRequest.filter((item) => item !== user));
      })
    } catch (e) {
      throw 'error catch GET DATA APP (getCurrentProfile)'
    }
  }


  //send rejected request to server
  const declineRequest = async (user) => {
    try{

      const rejectedRequest = {
        id: user.id,
        status: 'REJECTED',
      }

      await api_fetch.sendRejectedRequest(rejectedRequest);
      
      props.setUser((acceptedRequest) => acceptedRequest.filter((item) => item !== user))

      const usersOverview = await api_fetch.getUsersOverview();
      props.setEmployees(usersOverview);

  } catch (e) {
    alert(e)
    }
  }

  return (
    <div className="offs-request column">
      <h3>New Requests</h3>
      <div className="underline-1"></div>
      <div className="offs-items column">
        <div className="offs-item row">
          <table>
            {/* {props.userRequest.length > 0 
            ? */}
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
            {/* :
            <tbody>
              <p>There are no requests.</p>
            </tbody>
             } */}
            
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