'use strict';

const assert = require('assert');
const app = require('../../../src/app');
var request = require('supertest');

describe('products deletion', function () {
    beforeEach(function (done) {
      app.set('readonly', false);
      done();
    });
  
    it('sucessfully deletes an existing product', function (done) {
      request(app)
        .post('/products')
        .send({
          name: 'Product to delete',
          description: 'This is a test product',
          upc: '12345',
          type: 'Electronics',
          model: 'Product0123'
        })
        .expect(201)
        .end(function (err, result) {
          if (err) { assert.ifError(err); }
          var id = result.body.id;
  
          assert.ok(id);
  
          request(app)
            .delete('/products/' + id)
            .expect(200)
            .end(function (err, result) {
              if (err) { assert.ifError(err); }
              request(app)
                .get('/products/'+id)
                .expect(404, done);              
            });
        });
    });
  
    it('gives an error when product does not exist', function(done) {
      request(app)
        .delete('/products/100000000000000')
        .expect(404)
        .end(function (err, result) {
          if (err) { assert.ifError(err); }
          done();
          
        });
    });
});
  