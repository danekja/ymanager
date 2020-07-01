import React, { useState, useEffect } from 'react';
import './App.css';


const OverviewAdmin = () => {

   useEffect( () => {
      getData();
   }, []);

   const getData = async () => {
      try {
      const response = await fetch (
         'http://devcz.yoso.fi:8090/ymanager/users', {
          headers: {
            Authorization: 1          }
        }
      );
      
   if (response.ok) {

      const data = await response.json();
      setEmployees(data.map(user => {

         return (
            {
               name: user.firstName + ' ' + user.lastName,
               id: user.id,
               sickday: user.sickDayCount,
               holiday: user.vacationCount,
               role: user.role
            })
      }))
   }  else {
         if(response.status === 400) {
            alert('error 400 GET DATA (OVERVIEW, EMPLOYER)')
         }
            else if (response.status === 500) {
               alert ('error 500 GET DATA (OVERVIEW, EMPLOYER)')
            }
            else {
               alert('error GET DATA (OVERVIEW, EMPLOYER)')
            }
   }
} catch (e) {
   console.log(e)
   alert('spatne')
   }
}
   
   const [employees, setEmployees] = useState([
      {
         name: 'Sadam',
         id: 0,
         sickday: 10,
         holiday: 10
      }
   ]);

   const [isEdit, setEdit] = useState(false);
   const [editedUserId, setEditedUserId] = useState();
   
   const [prevEdit, setPrevEdit] = useState();

   function changeSickdays(newValue) {
      const newEmployees = employees.map(employee => {
         if (editedUserId === employee.id) {
            return {
               ...employee,
               sickday: newValue,
            };
         } else {
            return employee
         }
      })
      setEmployees(newEmployees);
   }

   function changeHoliday(newValue) {
      const newEmployees = employees.map(employee => {
         if (editedUserId === employee.id) {
            return {
               ...employee,
               holiday: newValue,
            };
         } else {
            return employee
         }
      })
      setEmployees(newEmployees);
   }

   const submitEdit = async (e) => {
      try {
      setEdit(isEdit === true ? false : true);
      setPrevEdit(employees);
      e.preventDefault();

      const found = employees.find(employee => editedUserId === employee.id);
      const foundPrevEdit = prevEdit.find(employee => editedUserId === employee.id);
      console.log(found)


   // send accepted request to server
       const response = await fetch('http://devcz.yoso.fi:8090/ymanager/user/settings', {
         headers: {
           Authorization: 1,
           'Content-Type': 'application/json',
         },
         method: 'PUT',

   // object which is sent to server
         body: JSON.stringify({
           id: found.id,
           vacationCount: Number(found.holiday) - foundPrevEdit.holiday,
           sickDayCount: Number(found.sickday),
           role: found.role
         }),        
       });
       console.log(response.status)
      if (response.status === 400) {
         alert('error 400 SAVE DATA (OVERVIEW, EMPLOYER)')
      }
         else if (response.status === 500) {
            alert('error 500 SAVE DATA (OVERVIEW, EMPLOYER)')
         }
            else if (!response.ok) {
               alert('error SAVE DATA (OVERVIEW, EMPLOYER)')
            }
                   
      } catch (e) {
         alert('error catch SAVE DATA (OVERVIEW, EMPLOYER')
      }
   }


   const cancel = () => {
      setEmployees(prevEdit)
      setEdit(false)
   }

   // ***** overview button ******

   const editEmployee = (employeeId, e) => {
      setEdit(true)
      setEditedUserId(employeeId)
      setPrevEdit(employees)
      
     e.preventDefault();
   }

   console.log(isEdit, editedUserId)
   
      return (
   <div>
      <div className="side-content">
         <div className="side-board column">
            <form className="column side-board-items" onSubmit={(e) => submitEdit(e)}>
            {/* <form className="column side-board-items" onSubmit={(e) => submitEdit(e)}> */}
            <div className="side-board-heading row">
               <h3>Overview</h3>
            </div>
            <div className="underline-1"></div>
            <div className="side-board-items column">
               <table border = "0">
               <tbody>
               <tr>
                  <th className="th-left">Name</th>
                  <th>Sick Days</th>
                  <th>Holiday</th>
                  <th></th>
               </tr>
                  {employees.map(employee => (
               <tr>
                  <td>{employee.name} {editedUserId}</td>
                  {/* SickDays Input */}
                  <td className="td-center">
                     {isEdit === true && editedUserId === employee.id ? (
                        <input className="offsInput" type="number" min="0" value={employee.sickday} onChange={(e) => changeSickdays(e.target.value)}/>
                     ) : (
                        employee.sickday
                     )}
                  </td>
                  {/* Holiday Input */}
                  <td className="td-center">
                     {isEdit === true && editedUserId === employee.id ? (
                       <input className="offsInput" type="number" min="0" value={employee.holiday} onChange={(e) => changeHoliday(e.target.value)}/>
                     ) : (
                        employee.holiday
                     )}</td>
                  {/* Edit Button */}
                  <button onClick={(e) => editEmployee(employee.id, e)} className="btn-edit">
                     <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-pencil" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M11.293 1.293a1 1 0 0 1 1.414 0l2 2a1 1 0 0 1 0 1.414l-9 9a1 1 0 0 1-.39.242l-3 1a1 1 0 0 1-1.266-1.265l1-3a1 1 0 0 1 .242-.391l9-9zM12 2l2 2-9 9-3 1 1-3 9-9z"/>
                        <path fill-rule="evenodd" d="M12.146 6.354l-2.5-2.5.708-.708 2.5 2.5-.707.708zM3 10v.5a.5.5 0 0 0 .5.5H4v.5a.5.5 0 0 0 .5.5H5v.5a.5.5 0 0 0 .5.5H6v-1.5a.5.5 0 0 0-.5-.5H5v-.5a.5.5 0 0 0-.5-.5H3z"/>
                     </svg>
                  </button>
                  {/* End of Edit Button */}
               </tr>
                  ))}
               </tbody>
            </table>
            {/* Submit & Reject Button */}
            <div className="column">
            {isEdit === true ? <button  type="submit" className="btn btn-submit">
            Save</button> : null}
            {isEdit === true ? (
            <button onClick={cancel} type="submit" className="btn btn-cancel">Cancel</button>
               ) : (null)}
            </div> 
          </div>
         </form>  
      </div>
   </div>
   </div>
   );    
   }


export default OverviewAdmin;
