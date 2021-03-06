import React, { useEffect } from 'react';
import './App.css';
import moment from 'moment';
import * as api from './api';
import convertVacationType from './convertVacationType';

export default function UpcomingRequests(props) {

  useEffect(() => {
    getData();
  }, []); // eslint-disable-line

  // get requests from server
  async function getData() {
    api.loadAdminRequests().then((data) => {
      props.setUser(data.map(request => {
        const convertedStartDate = request.date.split('/').join('-');

        return ({
          title: request.firstName + ' ' + request.lastName,
          id: request.id,
          type: convertVacationType(request.type),
          start: moment(convertedStartDate).format('D.M.YYYY'),
          end: null,
          status: request.status.toLowerCase(),
        });
      }));
    }).catch(reason => {
      alert(reason);
    });
  }

  // send accepted request to server
  async function acceptRequest(user) {
    try {
      const acceptedRequests = {
        id: user.id,
        status: 'ACCEPTED',
      };

      await api.sendAcceptedRequest(acceptedRequests).then(() => {
        const userProps = {
          title: user.title,
          type: user.type,
          start: user.start,
        };
        //concat new request to current ones
        props.setAcceptedRequest((acceptedRequest) => acceptedRequest.concat(userProps));
        //request accept button
        props.setUser((pendingRequest) => pendingRequest.filter((item) => item !== user));
      });
    } catch (e) {
      alert(e);
    }
  }

  //send rejected request to server
  async function declineRequest(user) {
    try {
      const rejectedRequest = {
        id: user.id,
        status: 'REJECTED',
      };

      await api.sendRejectedRequest(rejectedRequest);

      props.setUser((acceptedRequest) => acceptedRequest.filter((item) => item !== user));

      const usersOverview = await api.getUsersOverview();
      props.setEmployees(usersOverview);

    } catch (e) {
      alert(e);
    }
  }

  return (
    <div className="offs-request column">
      <h3>New Requests</h3>
      <div className="underline-1"></div>
      <div className="offs-items column">
        <div className="offs-item row">
          <table>
            {props.user.length > 0 ? (
              <tbody>
                <tr>
                  <th>Name</th>
                  <th>Type</th>
                  <th>Date</th>
                </tr>
                {props.user.map(user => (
                  <tr key={user.id}>
                    <td>{user.title}</td>
                    <td>{user.type}</td>
                    <td>{user.end ? user.start + ' - ' + user.end : user.start}</td>
                    <div className="offs-btn row">
                      <button onClick={() => acceptRequest(user)} type="submit" className="btn btn-submit">Accept</button>
                      <button onClick={() => declineRequest(user)} type="submit" className="btn btn-cancel">Decline</button>
                    </div>
                  </tr>
                ))}
              </tbody>
            ) : (
              <tbody>
                <p>There are no requests.</p>
              </tbody>
            )}
          </table>
        </div>
      </div>
    </div>
  );
}
