// ******************** GET DATA APP getCurrentProfile, [userName, setUserName] ********************

export const getCurrentProfile = async () => {

    try {
    const response = await fetch(
      'http://devcz.yoso.fi:8090/ymanager/users/current/profile', {
        headers: {
          Authorization: 1
        }
      }
    );

    if (response.ok) {
        const data = await response.json();
        return {
        name: data.firstName + ' ' + data.lastName,
        role: data.role,
        id: data.id,
        holiday: data.vacationCount,
        sickday: data.sickDayCount
    }
    } else {
        if(response.status === 400) {
        alert('error 400 GET DATA APP (getCurrentProfile)')
    }
        else if (response.status === 500) {
            alert ('error 500 GET DATA APP (getCurrentProfile)')
        }
        else {
            alert('error GET DATA APP (getCurrentProfile)')
        }
    }

} catch (e) {
  alert('error catch GET DATA APP (getCurrentProfile)')
  }
}

// ******************** LOAD DATA to CALENDAR - EMPLOYEE ********************
export const getUserCalendar = async (userName, fromDate ) => {
  try {
  const response = await fetch(
    `http://devcz.yoso.fi:8090/ymanager/user/${userName.id}/calendar?from=${fromDate}&status=ACCEPTED&status=REJECTED`, {
      headers: {
        'Accept': 'application/json',
        Authorization: 6
      },
      method: 'GET',
    }
  );

  if (response.ok) {
  const data = await response.json();
  
  return data.filter(day => {
    return day.status !== 'PENDING'
  }).map(day => {

    const newDate = day.date.split("/").join("-");

    return ({
    title: userName.name,
    start: newDate,
    backgroundColor: day.status === 'REJECTED' ? 'red' : 'green'
    })
  })
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

// ******************** LOAD DATA to CALENDAR - EMPLOYER ********************
export const getAdminCalendar = async () => {
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
    
    return data.map(day => {

      const newDate = day.date.split("/").join("-");

      return ( {
      title: day.firstName + ' ' + day.lastName,
      start: newDate
      })
    })
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

// ******************** ADD EVENT to CALENDAR - EMPLOYEE ********************
export const addEvent = async (e) => {
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

      return data.map(request => {
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
      })

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
    
//