function convertVacationType(vacationType) {
    switch (vacationType) {
      case 'SICK_DAY' :
        return 'sickday';
      default:
        return 'holiday';
    }
  }
  
  export default convertVacationType;