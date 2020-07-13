import React, { useState, useEffect } from 'react';
import './App.css';
import { Link } from 'react-router-dom';
import * as api_fetch from './api'


function Setting() {

  const [sickdays, setSickdays] = useState();

  useEffect( () => {
    api_fetch.getSettingData().then(settingData => {
      setSetting(settingData);
    }).catch(reason => {
      alert(reason)
    })
  }, []);

  const submitSetting = async (e) => {
    e.preventDefault();
    
    const dataSettingObject = {
      "sickDayCount": Number(setting.sickday),
      "notification": "2019/12/01 12:00:00"  
    }

    api_fetch.saveDataSetting(dataSettingObject).catch(reason => {
      alert(reason)
    })
  }
  // states
  const [setting, setSetting] = useState(
    {sickday: 5,
    holiday: 0
    }
  ) 

  //functions
  function changeSickday(newValue) {
    setSetting(
       {sickday: newValue,
        holiday: 0
    })
  }

  return (
    <div className="container">
      <div className="setting-container column">
        <h2>Setting</h2>
        <div className="underline-2"></div>
        <form onSubmit={(e) => submitSetting(e)} className="setting-form column">
          <label><h3>Sick days</h3></label>
          <input onChange={(e) => changeSickday(e.target.value)} value={setting.sickday} type="number" min="0" />
          <div className="buttons row">
            <Link to="/"><button className="btn btn-cancel" type="submit" value="Cancel">Cancel</button></Link>
            <button className="btn btn-submit" type="submit" value="Submit">Save</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Setting;