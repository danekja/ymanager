import React, { useState, useEffect } from 'react';
import './App.css';
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'
import '@fullcalendar/core/main.css'
import '@fullcalendar/daygrid/main.css'
import interactionPlugin from '@fullcalendar/interaction';
import Popup from "reactjs-popup";
import moment from 'moment';


function Calendar(props) {
  
  useEffect( () => {
    if (props.userName.id !== undefined) {
      props.userName.role === 'EMPLOYER' ? getDataAdmin() : getData();
    }
  }, [props.userName.id]);

  // LOAD DATA from server to calendar **** EMPLOYEE ****
  const getData = async () => {
    try {
    const response = await fetch(
      `http://devcz.yoso.fi:8090/ymanager/user/${props.userName.id}/calendar?from=${todayTwo}&status=ACCEPTED&status=REJECTED`, {
        headers: {
          'Accept': 'application/json',
          Authorization: 6
        },
        method: 'GET',
      }
    );

    if (response.ok) {
    const data = await response.json();
    
    props.setRequest(data.filter(day => {
      return day.status !== 'PENDING'
    }).map(day => {

      const newDate = day.date.split("/").join("-");

      return ({
      title: props.userName.name,
      start: newDate,
      backgroundColor: day.status === 'REJECTED' ? 'red' : 'green'
      })
    }))
  } else {
    if(response.status === 400) {
      alert('error 400 LOADING DATA (CALENDAR, EMPLOYEE)')
   }
      else if (response.status === 500) {
         alert ('error 500 LOADING DATA (CALENDAR, EMPLOYEE)')
      }
      else {
         alert('error LOADING DATA (CALENDAR, EMPLOYEE)')
      }
  }
  } catch (e) {
    alert('error catch LOADING DATA (CALENDAR, EMPLOYEE)')
  }
  }
  // LOAD DATA from server to calendar **** EMPLOYER ****
  const getDataAdmin = async () => {
    try {
    const response = await fetch(
      'http://devcz.yoso.fi:8090/ymanager/users/requests/vacation?status=ACCEPTED', {
        headers: {
          'Accept': 'application/json',
          Authorization: 1
        },
        method: 'GET',
      }
    );

    if (response.ok) {
    const data = await response.json();
    
    props.setRequest(data.map(day => {

      const newDate = day.date.split("/").join("-");

      return ( {
      title: day.firstName + ' ' + day.lastName,
      start: newDate
      })
    }))
  } else {
    if(response.status === 400) {
      alert('error 400 LOADING DATA (CALENDAR, EMPLOYER)')
   }
      else if (response.status === 500) {
         alert ('error 500 LOADING DATA (CALENDAR, EMPLOYER))')
      }
      else {
         alert('error LOADING DATA (CALENDAR, EMPLOYER)')
      }
  }
  } catch (e) {
    alert('error catch LOADING DATA (CALENDAR, EMPLOYER)')
  }
}


  //states
  const [isOpen, setOpen] = useState(false)

  const [whatDate, setDate] = useState('')

  const [whatTime, setWhatTime] = useState(7.5)

  const [typeRadio, setType] = useState('sickday')

  var today = new Date();

  // setting date to right format
  today = today.toISOString().split('T')[0]
  const todayTwo = today.split("-").join("/")


// ********************* ADD EVENT - EMPLOYEE **************************

  const addEvent = async (e) => {
    e.preventDefault();

  // setting an object
    const newDate = whatDate.split("-").join("/");
      
    try {
  // send accepted request to server
      const response = await fetch('http://devcz.yoso.fi:8090/ymanager/user/calendar/create', {
        headers: {
          Authorization: 6,
          'Content-Type': 'application/json',
        },
        method: 'POST',
  // object which is sent to server
        body: JSON.stringify({
          type: typeRadio === 'sickday' ? 'SICK_DAY' : 'VACATION',
          date: newDate,
          from: typeRadio === 'sickday' ? null : "00:00",
          to: typeRadio === 'sickday' ? null : moment().startOf('day').add(whatTime, "hours").format("hh:mm"),
        }),
      });
      if (response.ok) {

      const response = await fetch(
        'http://devcz.yoso.fi:8090/ymanager/users/requests/vacation?status=PENDING', {
          headers: {
            Authorization: 1
          },
        }
  
       );
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
    alert('error 400 ADD EVENT - EMPLOYEE')
 }
    else if (response.status === 500) {
       alert ('error 500 ADD EVENT - EMPLOYEE')
    }
    else {
       alert('error ADD EVENT - EMPLOYEE')
    }
    
  }
    } catch (e) {
      alert('error catch ADD EVENT - EMPLOYEE')
    }

    setOpen(false)}

  
// ********************* ADD EVENT ADMIN - EMPLOYER **************************

  const addEventAdmin = async (e) => {
    e.preventDefault();

  // setting an object
    const newDate = whatDate.split("-").join("/");
      
    try {
  // send accepted request to server
      const response = await fetch('http://devcz.yoso.fi:8090/ymanager/user/calendar/create', {
        headers: {
          Authorization: 1,
          'Content-Type': 'application/json',
        },
        method: 'POST',
  // object which is sent to server
        body: JSON.stringify({
          type: typeRadio === 'sickday' ? 'SICK_DAY' : 'VACATION',
          date: newDate,
          from: typeRadio === 'sickday' ? null : "00:00",
          to: typeRadio === 'sickday' ? null : moment().startOf('day').add(whatTime, "hours").format("hh:mm"),
        }),
      });
      if (response.ok) {
    

    const userProps = {
      title: props.userName.name,
      start: whatDate
    
  }
  //concat new request to current ones
      props.setRequest((acceptedRequest) => acceptedRequest.concat(userProps))
  } else {
    if(response.status === 400) {
      alert('error 400 ADD EVENT ADMIN - EMPLOYER')
   }
      else if (response.status === 500) {
         alert ('error 500 ADD EVENT ADMIN - EMPLOYER')
      }
      else {
         alert('error ADD EVENT ADMIN - EMPLOYER')
      }
  }
    } catch (e) {
      alert('error catch ADD EVENT ADMIN - EMPLOYER')
    }


    setOpen(false)}
    

  return (
    <div className="calendar">

    <FullCalendar defaultView="dayGridMonth" plugins={[ dayGridPlugin, interactionPlugin ]}

    dateClick={function(info) {
      //setOpen(true === info.dateStr > today ? true : false )
      setOpen(info.dateStr > today)
      setDate(info.dateStr)
      setWhatTime(7.5)
    }}
    events={[
      ...props.acceptedRequest
  ]}
    />

    <Popup 
    open={isOpen}
    position="right center" 
    modal
    closeOnDocumentClick
    onClose={() => setOpen(false)}
    >
    <div className="calendar-form">
      {/* <form onSubmit={(e) => addEvent(e)}> */}
      <form onSubmit={props.userName.role === 'EMPLOYER' ? (e) => addEventAdmin(e) : (e) => addEvent(e) }>
        <h2>Choose an option</h2>
        <div className="calendar-radio">
          <input checked={
            typeRadio === 'sickday' ? 'checked' : null}
            onChange={() => setType(typeRadio === 'holiday' ? 'sickday' : 'holiday')}
            type="radio" id="sickday" name="radiobutton" value="sickday"
          />
          <label for="sickday">Sickday</label>
        </div>
        <div className="calendar-radio">
          <input checked={
            typeRadio === 'holiday' ? 'checked' : null} 
            onChange={() => setType(typeRadio === 'holiday' ? 'sickday' : 'holiday')} 
            type="radio" id="holiday" name="radiobutton" value="holiday"
          />
          <label for="holiday">Extra Holiday</label>
        </div>
        <div>
          <h4>Date & Hours</h4>
          <div className="row">
            <input 
              className="date-input" 
              type='date' onChange={(e) => setDate(e.target.value)} 
              value={whatDate} min={today} 
            />
            {typeRadio === 'holiday' ? 
            <input
            className="input-time"
            step={0.5}
            min={0.5}
            max={7.5}
            type="number"
            onChange={(e) => setWhatTime(e.target.value)}
            required
            value={whatTime}
          /> : null}
             </div> 
          </div>
        <button className="btn btn-submit" type="submit">Submit</button>
      </form>
    </div>
    </Popup>

    </div>
  );
  }


export default Calendar;
