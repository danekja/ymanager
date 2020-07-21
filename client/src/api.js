import * as HttpStatus from './HttpStatus';

/**
 * Log Out
 */
export async function logOut() {
  let response;

  try {
    response = await fetch(`${window.config.baseUrl}/logout`, {
      credentials: 'include',
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (response.ok) {
    window.location.replace('/logout');
  } else {
    switch (response.status) {
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * GET DATA APP getCurrentProfile
 */
export async function getCurrentProfile() {
  let response;

  try {
    response = await fetch(`${window.config.baseUrl}/users/current/profile`, {
      credentials: 'include',
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (response.ok) {
    const data = await response.json();

    return {
      name: data.firstName + ' ' + data.lastName,
      role: data.role,
      id: data.id,
      holiday: data.vacationCount,
      sickday: data.sickDayCount,
      photo: data.photo,
      takenSickday: data.takenSickDayCount,
    };
  } else {
    switch (response.status) {
      case HttpStatus.UNAUTHORIZED:
        window.location.replace('/login');
        break;
      case HttpStatus.FORBIDDEN:
        window.location.replace('/login');
        break;
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * LOAD DATA to CALENDAR - EMPLOYEE
 */
export async function getUserCalendar(currentUser, fromDate ) {
  let response;

  try {
    response = await fetch(`${window.config.baseUrl}/user/${currentUser.id}/calendar?from=${fromDate}&status=ACCEPTED&status=REJECTED`, {
      headers: {
        'Accept': 'application/json',
      },
      credentials: 'include',
      method: 'GET',
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (response.ok) {
    const data = await response.json();

    return data.filter(day => {
      return day.status !== 'PENDING';
    }).map(day => {
      const newDate = day.date.split('/').join('-');

      return ({
        title: currentUser.name,
        start: newDate,
        backgroundColor: day.status === 'REJECTED' ? 'red' : 'green',
      });
    });
  } else {
    switch (response.status) {
      case HttpStatus.BAD_REQUEST:
        throw new Error('Bad request. Check query parameters.');
      case HttpStatus.UNAUTHORIZED:
        window.location.replace('/login');
        break;
      case HttpStatus.FORBIDDEN:
        window.location.replace('/login');
        break;
      case HttpStatus.NOT_FOUND:
        throw new Error('User with given ID does not exist.');
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * LOAD DATA to CALENDAR - EMPLOYER
 */
export async function getAdminCalendar() {
  let response;

  try {
    response = await fetch(`${window.config.baseUrl}/users/requests/vacation?status=ACCEPTED`, {
      headers: {
        'Accept': 'application/json',
      },
      credentials: 'include',
      method: 'GET',
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (response.ok) {
    const data = await response.json();

    return data.map(day => {

      const newDate = day.date.split('/').join('-');

      return {
        title: day.firstName + ' ' + day.lastName,
        start: newDate,
      };
    });
  } else {
    switch (response.status) {
      case HttpStatus.BAD_REQUEST:
        throw new Error('Bad request. Check query parameters.');
      case HttpStatus.UNAUTHORIZED:
        window.location.replace('/login');
        break;
      case HttpStatus.FORBIDDEN:
        window.location.replace('/login');
        break;
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * ADD EVENT to CALENDAR - EMPLOYEE
 */
export async function addEventApi(dataAddEventEmployee) {
  let response;

  try {
  // send accepted request to server
    response = await fetch(`${window.config.baseUrl}/user/calendar/create`, {
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      method: 'POST',
      // object which is sent to server
      body: JSON.stringify(dataAddEventEmployee),
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (!response.ok) {
    switch (response.status) {
      case HttpStatus.BAD_REQUEST:
        throw new Error('Bad request. Check request body.');
      case HttpStatus.UNAUTHORIZED:
        window.location.replace('/login');
        break;
      case HttpStatus.FORBIDDEN:
        window.location.replace('/login');
        break;
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * ADD EVENT to CALENDAR - EMPLOYER
 */
export async function addEventApiAdmin(dataAddEventAdmin) {
  let response;

  try {
    // send accepted request to server
    response = await fetch(`${window.config.baseUrl}/user/calendar/create`, {
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      method: 'POST',
      // object which is sent to server
      body: JSON.stringify(dataAddEventAdmin),
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (!response.ok) {
    switch (response.status) {
      case HttpStatus.BAD_REQUEST:
        throw new Error('Bad request. Check request body.');
      case HttpStatus.UNAUTHORIZED:
        window.location.replace('/login');
        break;
      case HttpStatus.FORBIDDEN:
        window.location.replace('/login');
        break;
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * GET DATA to OVERVIEW - EMPLOYER
 */
export async function getUsersOverview() {
  let response;

  try {
    response = await fetch (`${window.config.baseUrl}/users`, {
      credentials: 'include',
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (response.ok) {
    const data = await response.json();
    return data.map(user => {
      return {
        name: user.firstName + ' ' + user.lastName,
        id: user.id,
        sickday: user.sickDayCount,
        holiday: user.vacationCount,
        takenSickday: user.takenSickDayCount,
        role: user.role,
      };
    });
  } else {
    switch (response.status) {
      case HttpStatus.BAD_REQUEST:
        throw new Error('Bad request. Check query parameters.');
      case HttpStatus.UNAUTHORIZED:
        window.location.replace('/login');
        break;
      case HttpStatus.FORBIDDEN:
        window.location.replace('/login');
        break;
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * SAVE DATA to OVERVIEW - EMPLOYER
 */
export async function saveDataOverview(dataOverviewObject) {
  let response;

  try {
    // send accepted request to server
    response = await fetch(`${window.config.baseUrl}/user/settings`, {
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      method: 'PUT',
      // object which is sent to server
      body: JSON.stringify(dataOverviewObject),
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (!response.ok) {
    switch (response.status) {
      case HttpStatus.BAD_REQUEST:
        throw new Error('Bad request. Check query parameters.');
      case HttpStatus.UNAUTHORIZED:
        window.location.replace('/login');
        break;
      case HttpStatus.FORBIDDEN:
        window.location.replace('/login');
        break;
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * LOAD DATA to SETTING - EMPLOYER
 */
export async function getSettingData() {
  let response;

  try {
    response = await fetch(`${window.config.baseUrl}/settings`, {
      credentials: 'include',
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (response.ok) {
    const data = await response.json();
    return {
      sickday: data.sickDayCount,
    };
  } else {
    switch (response.status) {
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * SAVE DATA to SETTING - EMPLOYER
 */
export async function saveDataSetting(dataSettingObject) {
  let response;

  try {
    response = await fetch(`${window.config.baseUrl}/settings`, {
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      method: 'POST',
      body: JSON.stringify(dataSettingObject),
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (!response.ok) {
    switch (response.status) {
      case HttpStatus.UNAUTHORIZED:
        window.location.replace('/login');
        break;
      case HttpStatus.FORBIDDEN:
        window.location.replace('/login');
        break;
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * LOAD DATA to YOUR REQUESTS - EMPLOYEE
 */
export async function loadYourRequests(currentUser) {
  let response;

  try {
    response = await fetch(`${window.config.baseUrl}/user/${currentUser.id}/calendar?from=2020/06/24&status=PENDING`, {
      credentials: 'include',
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (response.ok) {
    const data = await response.json();
    return data;
  } else {
    switch (response.status) {
      case HttpStatus.BAD_REQUEST:
        throw new Error('Bad request. Check query parameters.');
      case HttpStatus.UNAUTHORIZED:
        window.location.replace('/login');
        break;
      case HttpStatus.FORBIDDEN:
        window.location.replace('/login');
        break;
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * LOAD DATA - UPCOMING REQUESTS - EMPLOYER
 */
export async function loadAdminRequests() {
  let response;

  try {
    response = await fetch(`${window.config.baseUrl}/users/requests/vacation?status=PENDING`, {
      credentials: 'include',
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (response.ok) {
    return response.json();
  } else {
    switch (response.status) {
      case HttpStatus.BAD_REQUEST:
        throw new Error('Bad request. Check query parameters.');
      case HttpStatus.UNAUTHORIZED:
        window.location.replace('/login');
        break;
      case HttpStatus.FORBIDDEN:
        window.location.replace('/login');
        break;
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * SEND ACCEPTED DATA - UPCOMING REQUESTS - EMPLOYER
 */
export async function sendAcceptedRequest(acceptedRequests) {
  let response;

  try {
    response = await fetch(`${window.config.baseUrl}/user/requests?type=VACATION`, {
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      method: 'PUT',
      body: JSON.stringify(acceptedRequests),
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (!response.ok) {
    switch (response.status) {
      case HttpStatus.BAD_REQUEST:
        throw new Error('Bad request. Check query parameters and request body.');
      case HttpStatus.UNAUTHORIZED:
        window.location.replace('/login');
        break;
      case HttpStatus.FORBIDDEN:
        window.location.replace('/login');
        break;
      case HttpStatus.NOT_FOUND:
        throw new Error('Neither vacation nor authorization request with given ID exists.');
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}

/**
 * SEND REJECTED DATA - UPCOMING REQUESTS - EMPLOYER
 */
export async function sendRejectedRequest(rejectedRequest) {
  let response;

  try {
    response = await fetch(`${window.config.baseUrl}/user/requests?type=VACATION`, {
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      method: 'PUT',
      body: JSON.stringify(rejectedRequest),
    });
  } catch (e) {
    throw new Error('Server is not available');
  }

  if (!response.ok) {
    switch (response.status) {
      case HttpStatus.BAD_REQUEST:
        throw new Error('Bad request. Check query parameters and request body.');
      case HttpStatus.UNAUTHORIZED:
        window.location.replace('/login');
        break;
      case HttpStatus.FORBIDDEN:
        window.location.replace('/login');
        break;
      case HttpStatus.NOT_FOUND:
        throw new Error('Neither vacation nor authorization request with given ID exists.');
      case HttpStatus.INTERNAL_SERVER_ERROR:
        throw new Error('Internal server error.');
      default:
        throw new Error(response.statusText);
    }
  }
}
