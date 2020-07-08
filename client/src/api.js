
const http = 'http://devcz.yoso.fi:8090/ymanager';

// ******************** GET DATA APP getCurrentProfile ********************

export const getCurrentProfile = async () => {

  let response;

  try {
    response = await fetch(
      `${http}/users/current/profile`, {
        headers: {
          Authorization: 1
          
        }
      }
    );    
  } catch (e) {
    throw 'Server is not available'
    }

  if (response.ok) {
    const data = await response.json();

    return {
      name: data.firstName + ' ' + data.lastName,
      role: data.role,
      id: data.id,
      holiday: data.vacationCount,
      sickday: data.sickDayCount,
      takenSickday: data.takenSickDayCount
    }
  } else {
      switch (response.status) {
        case 401:
          throw new Error('Not authenticated.')
        case 500:
          throw new Error('Internal server error.')
        default:
          throw new Error(response.statusText)
      }
    }
}

// ******************** LOAD DATA to CALENDAR - EMPLOYEE ********************
export const getUserCalendar = async (userName, fromDate ) => {

  let response;

  try {
    response = await fetch(
      `${http}/user/${userName.id}/calendar?from=${fromDate}&status=ACCEPTED&status=REJECTED`, {
        headers: {
          'Accept': 'application/json',
          Authorization: 6
        },
        method: 'GET',
      }
    );
  } catch (e) {
    throw 'Server is not available'
  }

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
      switch (response.status) {
        case 400:
          throw new Error('Bad request. Check query parameters.')
        case 401:
          throw new Error('Not authenticated.')
        case 403:
          throw new Error('Not authorized.')
        case 404:
          throw new Error('User with given ID does not exist.')
        case 500:
          throw new Error('Internal server error.')
        default:
          throw new Error(response.statusText)
      }
    }
}

// ******************** LOAD DATA to CALENDAR - EMPLOYER ********************

export const getAdminCalendar = async () => {

  let response;

  try {
    response = await fetch(
      `${http}/users/requests/vacation?status=ACCEPTED`, {
        headers: {
          'Accept': 'application/json',
          Authorization: 1
        },
        method: 'GET',
      }
    );
  } catch (e) {
    throw 'Server is not available'
  }

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
      switch (response.status) {
        case 400:
          throw new Error('Bad request. Check query parameters.')
        case 401:
          throw new Error('Not authenticated.')
        case 403:
          throw new Error('Not authorized.')
        case 500:
          throw new Error('Internal server error.')
        default:
          throw new Error(response.statusText)
      }
    }
}

// ******************** ADD EVENT to CALENDAR - EMPLOYEE ********************

export async function addEventApi(dataAddEventEmployee) {
  let response;

  try {
  // send accepted request to server
    response = await fetch(`${http}/user/calendar/create`, {
      headers: {
        Authorization: 6,
        'Content-Type': 'application/json',
      },
      method: 'POST',
  // object which is sent to server
  body: JSON.stringify(dataAddEventEmployee),
    });
  } catch (e) {
    throw 'Server is not available'
  }

  if (response.ok) {
      
    response = await fetch(
    `${http}/users/requests/vacation?status=PENDING`, {
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
      switch (response.status) {
        case 400:
          throw new Error('Bad request. Check request body.')
        case 401:
          throw new Error('Not authenticated.')
        case 403:
          throw new Error('Not authorized.')
        case 500:
          throw new Error('Internal server error.')
        default:
          throw new Error(response.statusText)
      }    
    }
}
      
// ******************** ADD EVENT to CALENDAR - EMPLOYER ********************

export async function addEventApiAdmin(dataAddEventAdmin) {
  let response;

  try {
  // send accepted request to server
    response = await fetch(`${http}/user/calendar/create`, {
      headers: {
        Authorization: 1,
        'Content-Type': 'application/json',
    },
      method: 'POST',
    // object which is sent to server
      body: JSON.stringify(dataAddEventAdmin),
    });
  } catch (e) {
    throw 'Server is not available'
  }

  if (!response.ok) {
    switch (response.status) {
      case 400:
        throw new Error('Bad request. Check request body.')
      case 401:
        throw new Error('Not authenticated.')
      case 403:
        throw new Error('Not authorized.')
      case 500:
        throw new Error('Internal server error.')
      default:
        throw new Error(response.statusText)
    }    
  }
}

// ******************** GET DATA to OVERVIEW - EMPLOYER ********************

export const getUsersOverview = async () => {
  let response;

  try {
  response = await fetch (
    `${http}/users`, {
      headers: {
        Authorization: 1          }
    }
  );
  } catch (e) {
    throw 'Server is not available'
  }
  
  if (response.ok) {

    const data = await response.json();
    return data.map(user => {

     return ({
           name: user.firstName + ' ' + user.lastName,
           id: user.id,
           sickday: user.sickDayCount,
           holiday: user.vacationCount,
           takenSickday: user.takenSickDayCount,
           role: user.role
        })
    })
  } else {
    switch (response.status) {
      case 400:
        throw new Error('Bad request. Check query parameters.')
      case 401:
        throw new Error('Not authenticated.')
      case 403:
        throw new Error('Not authorized.')
      case 500:
        throw new Error('Internal server error.')
      default:
        throw new Error(response.statusText)
    }    
  }
}

// ******************** SAVE DATA to OVERVIEW - EMPLOYER ********************

export async function saveDataOverview(dataOverviewObject) {
  let response;

  try {
    // send accepted request to server
        response = await fetch(`${http}/user/settings`, {
          headers: {
            Authorization: 1,
            'Content-Type': 'application/json',
          },
          method: 'PUT',
 
    // object which is sent to server
          body: JSON.stringify(dataOverviewObject),        
        });
  } catch (e) {
    throw 'Server is not available'
  }

  if (!response.ok) {
    switch (response.status) {
      case 400:
        throw new Error('Bad request. Check query parameters.')
      case 401:
        throw new Error('Not authenticated.')
      case 403:
        throw new Error('Not authorized.')
      case 500:
        throw new Error('Internal server error.')
      default:
        throw new Error(response.statusText)
    }
  }    
}

// ******************** LOAD DATA to SETTING - EMPLOYER ********************
export const getSettingData = async () =>  {
  let response;

  try {
    response = await fetch(
      `${http}/settings`, {
        headers: {
          Authorization: 1

        }
      });
  } catch (e) {
    throw 'Server is not available'
    }

  if (response.ok) {
    const data = await response.json();
    return {
      sickday: data.sickDayCount,
    }
  } else {
    switch (response.status) {
      case 500:
        throw new Error('Internal server error.')
      default:
        throw new Error(response.statusText)
      }            
    }

}

// ******************** SAVE DATA to SETTING - EMPLOYER ********************
export async function saveDataSetting(dataSettingObject) {
  let response;

  try {
    response = await fetch(`${http}/settings`, {
      headers: {
        'Authorization': 1,
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(dataSettingObject),
    });
  } catch (e) {
    throw 'Server is not available'
  }

  if (!response.ok) {
    switch (response.status) {
      case 401:
        throw new Error('Not authenticated.')
      case 403:
        throw new Error('Not authorized.')
      case 500:
        throw new Error('Internal server error.')
      default:
        throw new Error(response.statusText)
    }    
  }
}

// ****************** LOAD DATA to YOUR REQUESTS - EMPLOYEE ******************

export async function loadYourRequests() {
  let response;
  
  try {
    response = await fetch(
      `${http}/user/6/calendar?from=2020/06/24&status=PENDING`, {
        headers: {
          Authorization: 6
        },
      }
    );
  } catch (e) {
    throw 'Server is not available'
  }

  if (response.ok) {
    const data = await response.json();
    return data;
  } else {
      switch (response.status) {
        case 400:
          throw new Error('Bad request. Check query parameters.')
        case 401:
          throw new Error('Not authenticated.')
        case 403:
          throw new Error('Not authorized.')
        case 500:
          throw new Error('Internal server error.')
        default:
          throw new Error(response.statusText)
      }    
    }
}

// ****************** LOAD DATA - UPCOMING REQUESTS - EMPLOYER ****************** //tady
export async function loadAdminRequests() {
  let response;

  try {
    response = await fetch(
      `${http}/users/requests/vacation?status=PENDING`, {
        headers: {
          Authorization: 1
        }
      },
    );
  } catch (e) {
    throw 'Server is not available'
    } 
  
  if (response.ok) {
    const data = await response.json();
      return data;
  } else {
      switch (response.status) {
        case 400:
          throw new Error('Bad request. Check query parameters.')
        case 401:
          throw new Error('Not authenticated.')
        case 403:
          throw new Error('Not authorized.')
        case 500:
          throw new Error('Internal server error.')
        default:
          throw new Error(response.statusText)
      }   
    }
}

// ************** SEND ACCEPTED DATA - UPCOMING REQUESTS - EMPLOYER **************

export async function sendAcceptedRequest(acceptedRequests) {
  let response;

  try {
    response = await fetch(`${http}/user/requests?type=VACATION`, {
      headers: {
        Authorization: 1,
        'Content-Type': 'application/json',
      },
      method: 'PUT',
      body: JSON.stringify(acceptedRequests),
    });
  } catch (e) {
    throw 'Server is not available'
    }

  if (!response.ok) {
    switch (response.status) {
      case 400:
        throw new Error('Bad request. Check query parameters and request body.')
      case 401:
        throw new Error('Not authenticated.')
      case 403:
        throw new Error('Not authorized.')
      case 404:
        throw new Error('Neither vacation nor authorization request with given ID exists.')
      case 500:
        throw new Error('Internal server error.')
      default:
        throw new Error(response.statusText)
    }   
  }
}

// ************** SEND REJECTED DATA - UPCOMING REQUESTS - EMPLOYER **************

export async function sendRejectedRequest(rejectedRequest) {
  let response;
  
  try {
    response = await fetch(`${http}/user/requests?type=VACATION`, {
      headers: {
        Authorization: 1,
        'Content-Type': 'application/json',
      },
      method: 'PUT',
      body: JSON.stringify(rejectedRequest),
    });
  } catch (e) {
    throw 'Server is not available'
    }
  
  if (!response.ok) {
    switch (response.status) {
      case 400:
        throw new Error('Bad request. Check query parameters and request body.')
      case 401:
        throw new Error('Not authenticated.')
      case 403:
        throw new Error('Not authorized.')
      case 404:
        throw new Error('Neither vacation nor authorization request with given ID exists.')
      case 500:
        throw new Error('Internal server error.')
      default:
        throw new Error(response.statusText)
    }    
  }
}
