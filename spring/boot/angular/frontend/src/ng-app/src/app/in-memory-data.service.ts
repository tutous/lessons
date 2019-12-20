import { DataContainerOut } from './model/data-container/DataContainerOut';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Injectable } from '@angular/core';
import { DataContainerType } from './model/data-container/DataContainerType';
import { DataContainerState } from './model/data-container/DataContainerState';
import { UserRole } from './model/user/UserRole';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const dataContainers = this.createMockData();
    return {dataContainers};
  }

  createMockData(): DataContainerOut[] {
    const result: DataContainerOut[] = [];
    // for (let i = 0; i < 5; i++) {

    //   result.push(
    //     {
    //       id: i.toString(),
    //       name: 'DatenContainer_XYZ_' + i,
    //       description: 'Test DC ' + i,
    //       state: [DataContainerState.UNKNOWN],
    //       type: DataContainerType.UDS,
    //       vehicleClasses: ['ABC', 'DEF'],
    //       members: [{id: '0', userId: '0', fullName: 'Peter Flönk', userRole: UserRole.DATA_CONTAINER_REPRESENTAION }],
    //       createdBy: 'max.mueller',
    //       modifiedBy: 'max.mueller',
    //       modifiedOn: new Date(),
    //       createdOn: new Date(),
    //     }
    //   );
    // }

    return result;
  }

  // genId(dcs: DataContainerOut[]): string {
  //   return dcs.length > 0 ? Math.max(...dcs.map(dc => dc.id)) + 1 : 0;
  // }
}
