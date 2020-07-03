const http = 'http://devcz.yoso.fi:8090/ymanager';

// ******************** GET DATA APP getCurrentProfile ********************

export const getCurrentProfile = async () => {

    try {
    const response = await fetch(
      `${http}/users/current/profile`, {
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
          throw 'error 400 GET DATA APP (getCurrentProfile)'
        }
        else if (response.status === 500) {
          throw 'error 500 GET DATA APP (getCurrentProfile)'
        }
        else {
          throw 'error GET DATA APP (getCurrentProfile)'
        }
    }

} catch (e) {
  throw 'error catch GET DATA APP (getCurrentProfile)'
  }
}

// ******************** LOAD DATA to CALENDAR - EMPLOYEE ********************
export const getUserCalendar = async (userName, fromDate ) => {
  try {
  const response = await fetch(
    `${http}/user/${userName.id}/calendar?from=${fromDate}&status=ACCEPTED&status=REJECTED`, {
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
      throw 'error 400 LOADING DATA (CALENDAR, EMPLOYEE)'
    }
    else if (response.status === 500) {
      throw 'error 500 LOADING DATA (CALENDAR, EMPLOYEE)'
    }
    else {
      throw 'error LOADING DATA (CALENDAR, EMPLOYEE)'
    }
}
} catch (e) {
  throw 'error catch LOADING DATA (CALENDAR, EMPLOYEE)'
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
        throw 'error 400 LOADING DATA (CALENDAR, EMPLOYER)'
      }
      else if (response.status === 500) {
        throw 'error 500 LOADING DATA (CALENDAR, EMPLOYER))'
      }
      else {
        throw 'error LOADING DATA (CALENDAR, EMPLOYER)'
      }
    }
  } catch (e) {
    throw 'error catch LOADING DATA (CALENDAR, EMPLOYER)'
  }
}

// ******************** ADD EVENT to CALENDAR - EMPLOYEE ********************
export async function addEventApi(dataAddEventEmployee) {
try {
  // send accepted request to server
    const response = await fetch('http://devcz.yoso.fi:8090/ymanager/user/calendar/create', {
      headers: {
        Authorization: 6,
        'Content-Type': 'application/json',
      },
      method: 'POST',
  // object which is sent to server
  body: JSON.stringify(dataAddEventEmployee),
    });
    if (response.ok) {
      
      const response = await fetch(
      'http://devcz.yoso.fi:8090/ymanager/users/requests/vacation?status=PENDING', {
        headers: {
          Authorization: 1
        },
      });
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
      throw 'error 400 ADD EVENT - EMPLOYEE'
    }
    else if (response.status === 500) {
      throw 'error 500 ADD EVENT - EMPLOYEE'
    }
    else {
      throw 'error ADD EVENT - EMPLOYEE'
    }
  }
} catch (e) {
    throw 'error catch ADD EVENT - EMPLOYEE'
  }
}
      
// ******************** ADD EVENT to CALENDAR - EMPLOYER ********************
export async function addEventApiAdmin(dataAddEventAdmin) {
  try {
    // send accepted request to server
        const response = await fetch('http://devcz.yoso.fi:8090/ymanager/user/calendar/create', {
          headers: {
            Authorization: 1,
            'Content-Type': 'application/json',
          },
          method: 'POST',
    // object which is sent to server
          body: JSON.stringify(dataAddEventAdmin),
        });
        if (response.ok) {
          return;
    } else {
      if(response.status === 400) {
        throw('error 400 ADD EVENT ADMIN - EMPLOYER')
     }
        else if (response.status === 500) {
           throw ('error 500 ADD EVENT ADMIN - EMPLOYER')
        }
        else {
           throw('error ADD EVENT ADMIN - EMPLOYER')
        }
    }
  } catch (e) {
      throw('error catch ADD EVENT ADMIN - EMPLOYER')
    }
}

// ******************** GET DATA to OVERVIEW - EMPLOYER ********************
export const getUsersOverview = async () => {
  try {
  const response = await fetch (
     'http://devcz.yoso.fi:8090/ymanager/users', {
      headers: {
        Authorization: 1          }
    }
  );
  
if (response.ok) {

  const data = await response.json();
  return data.map(user => {

     return (
        {
           name: user.firstName + ' ' + user.lastName,
           id: user.id,
           sickday: user.sickDayCount,
           holiday: user.vacationCount,
           role: user.role
        })
  })
}  else {
      if(response.status === 400) {
        throw 'error 400 GET DATA (OVERVIEW, EMPLOYER)'
      }
      else if (response.status === 500) {
        throw 'error 500 GET DATA (OVERVIEW, EMPLOYER)'
      }
      else {
        throw 'error GET DATA (OVERVIEW, EMPLOYER)'
        }
      }
  } catch (e) {
      throw 'error catch GET DATA (OVERVIEW, EMPLOYER)'
  }
}

// ******************** SAVE DATA to OVERVIEW - EMPLOYER ********************
export async function saveDataOverview(dataOverviewObject) {
  try {
    // send accepted request to server
        const response = await fetch('http://devcz.yoso.fi:8090/ymanager/user/settings', {
          headers: {
            Authorization: 1,
            'Content-Type': 'application/json',
          },
          method: 'PUT',
 
    // object which is sent to server
          body: JSON.stringify(dataOverviewObject),        
        });
        console.log(response.status)
       if (response.status === 400) {
        throw 'error 400 SAVE DATA (OVERVIEW, EMPLOYER)'
          }
      else if (response.status === 500) {
        throw 'error 500 SAVE DATA (OVERVIEW, EMPLOYER)'
      }
      else if (!response.ok) {
        throw 'error SAVE DATA (OVERVIEW, EMPLOYER)'
      }

  } catch (e) {
    throw 'error catch SAVE DATA (OVERVIEW, EMPLOYER'
  }
}

// ******************** LOAD DATA to SETTING - EMPLOYER ********************
export const getSettingData = async () =>  {
  try {
    const response = await fetch(
      'http://devcz.yoso.fi:8090/ymanager/settings', {
        headers: {
          Authorization: 1
        }
      });

      if (response.ok) {
      const data = await response.json();
      return {
        sickday: data.sickDayCount,
      }
    } else {
        if(response.status === 400) {
          throw 'error 400 LOADING DATA (SETTING, EMPLOYER)'
        }
        else if (response.status === 500) {
           throw 'error 500 LOADING DATA (SETTING, EMPLOYER)'
        }
        else {
           throw 'error LOADING DATA (SETTING, EMPLOYER)'
        }
      }
  } catch (e) {
    throw 'error catch LOADING DATA (SETTING, EMPLOYER)'
    }
}

// ******************** SAVE DATA to SETTING - EMPLOYER ********************
export async function saveDataSetting(dataSettingObject) {
  try {
    const response = await fetch('http://devcz.yoso.fi:8090/ymanager/settings', {
      headers: {
        'Authorization': 6,
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(dataSettingObject),
    });

    switch (response.status) {
      case 200:
        throw '...'
      case 500:
        throw ''
      default:
        throw response.statusText

    }

    if(response.status === 400) {
      throw 'error 400 SAVE DATA (OVERVIEW, EMPLOYER)'
    }
    else if (response.status === 500) {
      throw 'error 500 SAVE DATA (OVERVIEW, EMPLOYER)'
    }
    else if (!response.ok) {
      throw 'error SAVE DATA (OVERVIEW, EMPLOYER)'
      }
  } catch (e) {
      throw 'error catch SAVE DATA (OVERVIEW, EMPLOYER)'
    }
}

// ****************** LOAD DATA to YOUR REQUESTS - EMPLOYEE ******************

export async function loadYourRequests() {
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
    return data;
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

// ****************** LOAD DATA - UPCOMING REQUESTS - EMPLOYER ******************
export async function loadAdminRequests() {
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
      return data;
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
  alert('error catch GET DATA (UPCOMING REQUESTS)')
  } 
}

// ************** SEND ACCEPTED DATA - UPCOMING REQUESTS - EMPLOYER **************
export async function sendAcceptedRequest(acceptedRequests) {
  try {
    const response = await fetch('http://devcz.yoso.fi:8090/ymanager/user/requests?type=VACATION', {
      headers: {
        Authorization: 1,
        'Content-Type': 'application/json',
      },
      method: 'PUT',
      body: JSON.stringify(acceptedRequests),
    });

    if (response.ok) {
    return;

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

// ************** SEND REJECTED DATA - UPCOMING REQUESTS - EMPLOYER **************

export async function sendRejectedRequest(rejectedRequest) {
  try {
    const response = await fetch('http://devcz.yoso.fi:8090/ymanager/user/requests?type=VACATION', {
      headers: {
        Authorization: 1,
        'Content-Type': 'application/json',
      },
      method: 'PUT',
      body: JSON.stringify(rejectedRequest),
    });

  if (response.ok) {    
    return;

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
  alert('error catch SEND REJECTED DATA (UPCOMING REQUESTS)')
  }
}


