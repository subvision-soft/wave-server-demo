import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitorTableComponent } from './competitor-table.component';

describe('CompetitorTableComponent', () => {
  let component: CompetitorTableComponent;
  let fixture: ComponentFixture<CompetitorTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompetitorTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CompetitorTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
