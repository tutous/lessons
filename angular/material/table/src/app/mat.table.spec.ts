import { TestBed, async } from '@angular/core/testing';
import { MatTable } from './mat.table';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        MatTable
      ],
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(MatTable);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

});
