import React, { useState, useEffect } from 'react';
import './App.css';
import * as api_fetch from './api'


const OverviewAdmin = () => {

   useEffect(() => {
      api_fetch.getUsersOverview().then(usersOverview => {
         setEmployees(usersOverview);
         }).catch(reason => {
         alert(reason)
      });
   }, []);

   // states
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
   

   // functions
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
      
      setEdit(isEdit === true ? false : true);
      setPrevEdit(employees);
      e.preventDefault();

      const found = employees.find(employee => editedUserId === employee.id);
      const foundPrevEdit = prevEdit.find(employee => editedUserId === employee.id);

      const dataOverviewObject = {
         id: found.id,
         vacationCount: Number(found.holiday) - foundPrevEdit.holiday,
         sickDayCount: Number(found.sickday),
         role: found.role
      }

      api_fetch.saveDataOverview(dataOverviewObject).catch(reason => {
         alert(reason)
       });
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
