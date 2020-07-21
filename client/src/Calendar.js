import React, { useState, useEffect } from 'react';
import './App.css';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import '@fullcalendar/core/main.css';
import '@fullcalendar/daygrid/main.css';
import interactionPlugin from '@fullcalendar/interaction';
import Popup from 'reactjs-popup';
import moment from 'moment';
import * as api from './api';

const DEFAULT_MANDAY_HOURS = 7.5;
const SICK_DAY_COUNT_INCREMENT = 1;

export default function Calendar(props) {

  useEffect(() => {
    if (props.currentUser === undefined) {
      return;
    }

    if (props.currentUser.role === 'EMPLOYER') {
      api.getAdminCalendar().then(adminCalendar => {
        props.setAcceptedRequest(adminCalendar);
      }).catch(reason => {
        alert(reason);
      });
    } else {
      api.getUserCalendar(props.currentUser, convertedDate).then(userCalendar => {
        props.setAcceptedRequest(userCalendar);
      }).catch(reason => {
        alert(reason);
      });
    }
  }, [props.currentUser]);

  //states
  const [isOpen, setOpen] = useState(false);
  const [whatDate, setDate] = useState('');
  const [whatTime, setWhatTime] = useState(DEFAULT_MANDAY_HOURS);
  const [typeRadio, setType] = useState('sickday');

  var today = new Date();

  // setting date to right format
  today = today.toISOString().split('T')[0];
  const convertedDate = today.split('-').join('/');

  // ********************* ADD EVENT - EMPLOYEE **************************

  async function addEvent(e) {
    e.preventDefault();

    try {
      // setting an object
      const newDate = whatDate.split('-').join('/');

      const dataAddEventEmployee = {
        type: typeRadio === 'sickday' ? 'SICK_DAY' : 'VACATION',
        date: newDate,
        from: typeRadio === 'sickday' ? null : '00:00',
        to: typeRadio === 'sickday' ? null : moment().startOf('day').add(whatTime, 'hours').format('hh:mm'),
      };

      await api.addEventApi(dataAddEventEmployee);

      if (typeRadio === 'holiday') {
        props.setCurrentUser({
          ...props.currentUser,
          holiday: props.currentUser.holiday - whatTime,
        });
      } else if (typeRadio === 'sickday') {
        props.setCurrentUser({
          ...props.currentUser,
          takenSickday: props.currentUser.takenSickday + SICK_DAY_COUNT_INCREMENT,
        });
      }

      setOpen(false);
    } catch (e) {
      alert(e);
    }
  }

  // ********************* ADD EVENT ADMIN - EMPLOYER **************************

  async function addEventAdmin(e) {
    e.preventDefault();

    // setting an object
    const newDate = whatDate.split('-').join('/');

    const dataAddEventAdmin = {
      type: typeRadio === 'sickday' ? 'SICK_DAY' : 'VACATION',
      date: newDate,
      from: typeRadio === 'sickday' ? null : '00:00',
      to: typeRadio === 'sickday' ? null : moment().startOf('day').add(whatTime, 'hours').format('hh:mm'),
    };

    api.addEventApiAdmin(dataAddEventAdmin).then(() => {
      const userProps = {
        title: props.currentUser.name,
        start: whatDate,
      };
      //concat new request to current ones
      props.setAcceptedRequest((acceptedRequest) => acceptedRequest.concat(userProps));
    }).catch(reason => {
      alert(reason);
    });

    setOpen(false);

    api.getUsersOverview().then(usersOverview => {
      props.setEmployees(usersOverview);
    }).catch(reason => {
      alert(reason);
    });
  }

  return (
    <div className="calendar">
      <FullCalendar
        defaultView="dayGridMonth"
        plugins={[dayGridPlugin, interactionPlugin]}
        dateClick={function(info) {
          setOpen(info.dateStr > today);
          setDate(info.dateStr);
          setWhatTime(DEFAULT_MANDAY_HOURS);
        }}
        events={[
          ...props.acceptedRequest,
        ]}/>

      <Popup
        open={isOpen}
        position="right center"
        modal
        closeOnDocumentClick
        onClose={() => setOpen(false)}
      >
        <div className="calendar-form">
          <form onSubmit={props.currentUser !== undefined && props.currentUser.role === 'EMPLOYER' ? (e) => addEventAdmin(e) : (e) => addEvent(e)}>
            <h2>Choose vacation type</h2>
            <div className="calendar-radio">
              <input checked={typeRadio === 'sickday' ? 'checked' : null}
                onChange={() => setType(typeRadio === 'holiday' ? 'sickday' : 'holiday')}
                type="radio" id="sickday" name="radiobutton" value="sickday"
              />
              <label htmlFor="sickday">Sickday</label>
            </div>
            <div className="calendar-radio">
              <input checked={typeRadio === 'holiday' ? 'checked' : null}
                onChange={() => setType(typeRadio === 'holiday' ? 'sickday' : 'holiday')}
                type="radio" id="holiday" name="radiobutton" value="holiday"
              />
              <label htmlFor="holiday">Extra Holiday</label>
            </div>
            <div>
              {typeRadio === 'holiday' ? <h4>Date &amp; Hours</h4> : <h4>Date</h4>}
              <div className="row calendarInputs">
                <input
                  className="date-input"
                  type='date' onChange={(e) => setDate(e.target.value)}
                  value={whatDate} min={today}
                />
                {typeRadio === 'holiday' ? (
                  <input
                    className="input-time"
                    step={0.5}
                    min={0.5}
                    max={7.5}
                    type="number"
                    onChange={(e) => setWhatTime(e.target.value)}
                    required
                    value={whatTime}/>
                ) : null}
              </div>
            </div>
            <button className="btn btn-submit" type="submit">Request vacation</button>
          </form>
        </div>
      </Popup>
    </div>
  );
}
