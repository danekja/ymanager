import React, { useState, useEffect } from 'react';
import './App.css';
import { Link } from 'react-router-dom';


function Setting() {

  const [sickdays, setSickdays] = useState([]);

  useEffect( () => {
    getData();
  }, []);

  const getData = async () =>  {
    try {
      const response = await fetch(
        'http://devcz.yoso.fi:8090/ymanager/settings', {
          headers: {
            Authorization: 1
          }
        }
        );

        if (response.ok) {
        const data = await response.json();
        setSetting({
          sickday: data.sickDayCount,
        })
      } else {
        if(response.status === 400) {
          alert('error 400')
       }
          else if (response.status === 500) {
             alert ('error 500')
          }
          else {
             alert('spatne neco jinyho')
          }
      }
    } catch (e) {
      console.log(e)
      alert('spatne vsechno')
      }
  }

  const submitSetting = async (e) => {
    e.preventDefault();
    try {
    const response = await fetch('http://devcz.yoso.fi:8090/ymanager/settings', {
      headers: {
        'Authorization': 1,
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify({
        "sickDayCount": Number(setting.sickday),
        "notification": "2019/12/01 12:00:00"
      }),
    });


    if (response.ok) {
  } else {
    if(response.status === 400) {
      alert('error 400')
   }
      else if (response.status === 500) {
         alert ('error 500')
      }
      else {
         alert('spatne neco jinyho')
      }
  }
} catch (e) {
  console.log(e)
  alert('spatne vsechno')
  }

  }



  const [setting, setSetting] = useState(
    {sickday: 5,
    holiday: 0
    }
  ) 

  function changeSickday(newValue) {
    setSetting(
       {sickday: newValue,
        holiday: setting.holiday
      }
    )
  }

  function changeHoliday(newValue) {
    setSetting(
      {sickday: setting.sickday,
       holiday: newValue
      }
    )
  }

  return (
    <div className="container">
      <div className="setting-container column">
        <h2>Nastavení</h2>
        <div className="underline-2"></div>
        <form onSubmit={(e) => submitSetting(e)} className="setting-form column">
          <label><h3>Sick days</h3></label>
          <input onChange={(e) => changeSickday(e.target.value)} value={setting.sickday} type="number" min="0" />
          <label><h3>Holiday</h3></label>
          <input onChange={(e) => changeHoliday(e.target.value)} value={setting.holiday} type="number" min="0" />
          <div className="buttons row">
            <Link to="/"><button className="btn btn-cancel" type="submit" value="Cancel">Cancel</button></Link>
            <button className="btn btn-submit" type="submit" value="Submit">Submit</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Setting;