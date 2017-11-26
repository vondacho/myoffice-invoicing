import {JsonCleaner} from './json-cleaner';

describe('JsonCleaner', () => {

  [
    // idempotence testing
    [{a: 123, b: '123'}, {a: 123, b: '123'}],
    [{a: {a: 123, b: '123'}}, {a: {a: 123, b: '123'}}],
    [{a: [123, '123']}, {a: [123, '123']}],
    [[123, '123'], [123, '123']],
    [[{a: 123, b: '123'}], [{a: 123, b: '123'}]],
    [[{a: [123, '123']}], [{a: [123, '123']}]],

    // cleanup testing
    [ {a: null}, {}],
    [ {a: [null]}, {}],
    [ {a: ''}, {}],
    [ [null], []],
    [ [{a: null}], [{}]],
    [ [{a: [null]}], [{}]],
    [ [{a: ''}], [{}]],
    [ {a: {a: null}}, {a: {}}],
    [ {a: [{a: '', b: 'toto'}]}, {a: [{b: 'toto'}]}]
  ]
    .forEach(value =>
      it('should keep non-empty values deeply correctly ' + JSON.stringify(value[0]), () => {
        expect(JsonCleaner.clean(value[0])).toEqual(value[1]);
      }));
});
