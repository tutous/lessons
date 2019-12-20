import { UserStoreState } from 'src/app/shared/store/user-store/user.state';
import { Component } from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import { Store } from '@ngrx/store';
import * as UserActions from './shared/store/user-store/user.actions';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(
    translateService: TranslateService,
    private userStore: Store<UserStoreState>) {

    translateService.setDefaultLang('de');
    translateService.use('de');

    this.userStore.dispatch(UserActions.searchCurrentUser());
  }
}
