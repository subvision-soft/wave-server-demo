import {ResultPipe} from './result.pipe';
import {TargetModel} from "../models/target.model";
import {EventEnum} from "../models/event.enum";
import {ImpactModel} from "../models/impact.model";
import {ZoneEnum} from "../models/zone.enum";


let getTarget = () => {
  return {
    armedBeforeCountdown: false,
    badArrowExtractionsCount: 0,
    competitionId: 0,
    competitorId: 0,
    date: new Date(),
    departureSteal: false,
    id: 0,
    image: "",
    pictureId: 0,
    shotsTooCloseCount: 0,
    targetSheetNotTouchedCount: 0,
    time: 0,
    timeRanOut: false,
    total: 0,
    user: "",
    event: EventEnum.SUPER_BIATHLON,
    impacts: []
  };
}

const getValidImpact = (zone:ZoneEnum,score:number = 471):ImpactModel => {
  return {
    zone: zone,
    distance: 0,
    score: score,
    angle: 0,
    amount: 0
  };
}

const get5ValidImpacts = ():ImpactModel[] => {
  let impacts:ImpactModel[] = [];
  impacts.push(getValidImpact(ZoneEnum.TOP_LEFT));
  impacts.push(getValidImpact(ZoneEnum.TOP_RIGHT));
  impacts.push(getValidImpact(ZoneEnum.BOTTOM_LEFT));
  impacts.push(getValidImpact(ZoneEnum.BOTTOM_RIGHT,0));
  impacts.push(getValidImpact(ZoneEnum.CENTER));
  return impacts;
}
const get3ValidImpacts = ():ImpactModel[] => {
  let impacts:ImpactModel[] = [];
  impacts.push(getValidImpact(ZoneEnum.TOP_LEFT));
  impacts.push(getValidImpact(ZoneEnum.TOP_RIGHT));
  impacts.push(getValidImpact(ZoneEnum.BOTTOM_LEFT));
  return impacts;
}

describe('ResultPipe', () => {
  it('create an instance', () => {
    const pipe = new ResultPipe();
    expect(pipe).toBeTruthy();
  });
  it('should return 0 if time is higher than 10 minutes', () => {
    const pipe = new ResultPipe();
    const target:TargetModel = getTarget();
    target.impacts = get5ValidImpacts();
    target.time = 600001;
    expect(pipe.transform(target)).toBe(0);
    target.time = 600000;
    expect(pipe.transform(target)).toBe(3479);
  });
  it('should return 0 if 0 impacts for super biathlon', () => {
    const pipe = new ResultPipe();
    const target:TargetModel = getTarget();
    expect(pipe.transform(target)).toBe(0);
  });

  it('should return 0 if 0 impacts for biathlon', () => {
    const pipe = new ResultPipe();
    const target:TargetModel = getTarget();
    target.event = EventEnum.BIATHLON;
    expect(pipe.transform(target)).toBe(0);
  });

  it('should return score if 3 impacts for biathlon', () => {
    const pipe = new ResultPipe();
    const target:TargetModel = getTarget();
    target.event = EventEnum.BIATHLON;
    target.impacts = get3ValidImpacts();
    let time = 140;
    target.time = time * 1000;
    let expected = (471*3 - (time * 2)) * 3;
    expect(pipe.transform(target)).toBe(expected);
  });
  it ('should remove 50 points for each penalty biathlon', () => {

      const pipe = new ResultPipe();
      const target:TargetModel = getTarget();
      target.event = EventEnum.BIATHLON;
      target.impacts = get3ValidImpacts();
      let time = 140;
      target.time = time * 1000;
      target.shotsTooCloseCount = 1;
      target.badArrowExtractionsCount = 1;
      target.targetSheetNotTouchedCount = 1;
      target.departureSteal = true;
      target.armedBeforeCountdown = true;
      let penalties = (target.shotsTooCloseCount + target.badArrowExtractionsCount + target.targetSheetNotTouchedCount + (target.departureSteal ? 1 : 0) + (target.armedBeforeCountdown ? 1 : 0)) * 50;
      let expected = (471*3 - (time * 2)) * 3 - penalties;
      expect(pipe.transform(target)).toBe(expected);
  }
  )

  it('should return 0 if 0 impacts for precision', () => {
    const pipe = new ResultPipe();
    const target:TargetModel = getTarget();
    target.event = EventEnum.PRECISION;
    expect(pipe.transform(target)).toBe(0);
  });



});
