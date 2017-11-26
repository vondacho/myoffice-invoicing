import {isNullOrUndefined, isObject} from 'util';
import * as _ from 'lodash';

export class JsonCleaner {

  /**
   * Recursively remove properties having empty values from a JavaScript object
   * @param an object
   * @returns the cleaned object
   */
  static clean(obj: any): any {
    if (_.isArray(obj)) { // rebuild array recursively by inserting only not empty items
      const cleanArray: any[] = [];
      for (const item of obj) {
        const cleanItem = this.clean(item);
        if (!this.emptyValue(cleanItem)) {
          cleanArray.push(cleanItem);
        }
      }
      return cleanArray;
    } else if (isObject(obj)) { // rebuild object properties recursively by inserting only not empty properties
      const cleanObject: any = {};
      for (const field of Object.keys(obj)) {
        const cleanValue = this.clean(obj[field]);
        if (!this.emptyValue(cleanValue)) {
          cleanObject[field] = cleanValue;
        }
      }
      return cleanObject;
    } else { // end of recursion
      return obj;
    }
  }

  private static emptyValue(property): boolean {
    return isNullOrUndefined(property) || ((_.isArray(property) || _.isString(property)) && _.isEmpty(property));
  }
}
