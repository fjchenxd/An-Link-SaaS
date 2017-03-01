import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SirqulAccountDetailComponent } from '../../../../../../main/webapp/app/entities/sirqul-account/sirqul-account-detail.component';
import { SirqulAccountService } from '../../../../../../main/webapp/app/entities/sirqul-account/sirqul-account.service';
import { SirqulAccount } from '../../../../../../main/webapp/app/entities/sirqul-account/sirqul-account.model';

describe('Component Tests', () => {

    describe('SirqulAccount Management Detail Component', () => {
        let comp: SirqulAccountDetailComponent;
        let fixture: ComponentFixture<SirqulAccountDetailComponent>;
        let service: SirqulAccountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [SirqulAccountDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    SirqulAccountService
                ]
            }).overrideComponent(SirqulAccountDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SirqulAccountDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SirqulAccountService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new SirqulAccount(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sirqulAccount).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
