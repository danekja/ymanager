export default function convertVacationType(vacationType) {
  switch (vacationType) {
    case 'SICK_DAY' :
      return 'sickday';
    case 'HOLIDAY':
      return 'holiday';
    default:
      throw new Error(`Vacation type ${vacationType} is not supported here!`);
  }
}
